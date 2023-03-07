import React, { useEffect, useState } from "react";
import {
  CButton,
  CCard,
  CCardBody,
  CCardHeader,
  CCol,
  CForm,
  CFormGroup,
  CInput,
  CLabel,
  CSelect,
  CAlert,
  CRow,
} from "@coreui/react";
import { useDispatch, useSelector } from "react-redux";
import * as Yup from "yup";
import { useFormik } from "formik";
import { queryApi } from "../../utils/queryApi";
import { useHistory } from "react-router";
import {
  addEvaluation,
  fecthEvaluations,
  selectEvaluations,
} from "../../redux/slices/EvaluationSlice";
import { selectSelectedFiche } from "../../redux/slices/StudentSlice";
import { selectetudiant } from "../../redux/slices/FichePFESlice";
import {
  fetchEvaluationTypes,
  selectEvaluationTypes,
} from "../../redux/slices/ListingSlice";

const validationSchema = Yup.object().shape({
  noteEvaluation: Yup.number("La note doit être un numéro entre 0 et 20 ")
    .min(0, " La note doit être un numéro entre 0 et 20  ! ")
    .max(20, " La note doit être un numéro entre 0 et 20  ! ")
    .positive("* La note doit être un numéro entre 0 et 20 !")
    .required("* Champ obligatoire !"),
  tauxEvaluation: Yup.number("Le taux doit être 0 et 100 % ! ")
    .min(0, " Le taux doit être 0 et 100 % ! ")
    .max(100, " Le taux doit être 0 et 100 % ! ")
    .positive(" Le taux doit être 0 et 100 % ! ")
    .required("* Champ obligatoire !"),
  evaluationStagePK: Yup.object().shape({
    codeEvaluation: Yup.string().required("*Champ obligatoire !"),
  }),
});
const AddEvaluation = () => {
  const Etu = useSelector(selectetudiant);
  const dispatch = useDispatch();
  const history = useHistory();
  const [showError, setshowError] = useState(false);
  const Fiche = useSelector(selectSelectedFiche);
  const [error, setError] = useState({ visible: false, message: "" });
  const [evaluations, errEval] = useSelector(selectEvaluations);

  const [evaluationsTypes, errEvaluationsTypes] = useSelector(
    selectEvaluationTypes
  );
  useEffect(() => {
    if (evaluationsTypes === null || evaluationsTypes.length === 0) {
      dispatch(fetchEvaluationTypes());
    }
    if (evaluations === null || evaluations.length === 0) {
      dispatch(
        fecthEvaluations(Fiche.idFichePFE.idEt, Fiche.idFichePFE.dateDepotFiche)
      );
    }
    if (!Fiche) history.replace("/PFESheetDetails");
  }, [Fiche, dispatch, history]);

  const AddTeacherEval = async (values) => {
    const [res, err] = await queryApi(
      "encadrement/saisiEvalEncadrantPedaNote",
      values,
      "POST",
      false
    );
    if (err) {
      setError({
        visible: true,
        message: JSON.stringify(err.errors, null, 2),
      });
    } else {
      dispatch(addEvaluation(res));
      sessionStorage.setItem("DataIndex", 4);
      history.push("/PFESheetDetails");
    }
  };
  const formik = useFormik({
    initialValues: {
      evaluationStagePK: {
        fichePFEPK: {
          idEt: Fiche.idFichePFE.idEt,
          dateDepotFiche: Fiche.idFichePFE.dateDepotFiche,
        },
        codeEvaluation: "",
      },
      noteEvaluation: 0,
      tauxEvaluation: 0,
    },
    validationSchema: validationSchema,
    onSubmit: async (values) => {
      if (evaluations.length === 0) {
        AddTeacherEval(values);
      }
      if (evaluations.length !== 0) {
        const a = evaluations.find(
          (item) =>
            item.evaluationStagePK.fichePFEPK.idEt ===
              values.evaluationStagePK.fichePFEPK.idEt &&
            item.evaluationStagePK.fichePFEPK.dateDepotFiche ===
              values.evaluationStagePK.fichePFEPK.dateDepotFiche &&
            item.evaluationStagePK.codeEvaluation ===
              values.evaluationStagePK.codeEvaluation
        );
        if (a === undefined) {
          AddTeacherEval(values);
        } else {
          setshowError(true);
          formik.values.noteEvaluation = 0;
          formik.values.tauxEvaluation = 0;
        }
      }
    },
  });
  return (
    <>
      <div>
        <CCol xs="12" md="12" className="mb-4">
          <CCard>
            <CCardHeader>
              <CRow>
                
                <CCol xs="12" sm="6" md="6">
                  <h5>Saisir une note </h5>
                </CCol>
                <CCol xs="12" sm="6" md="6">
                  <h4 className="float-right">
                    <b>
                      {Etu.prenomet} {Etu.nomet} &nbsp;&nbsp; {Etu.classe}
                    </b>
                  </h4>
                  <br></br> <br></br>
                  <h6 className="float-right"> {Etu.adressemailesp}</h6>
                </CCol>
              </CRow>
            </CCardHeader>
            <CCardBody>
              <CForm onSubmit={formik.handleSubmit}>
                <CFormGroup>
                  
                  {error.visible && <p>{error.message}</p>}
                </CFormGroup>
                <CFormGroup row>
                  <CCol md="4">
                    <CLabel htmlFor="selectSm">
                      <b>Type d'évaluation</b>
                    </CLabel>
                  </CCol>
                  <CCol xs="12" md="8">
                    <CSelect
                      value={formik.values.evaluationStagePK.codeEvaluation}
                      onChange={(e) => {
                        // call the built-in handleBur
                        formik.handleChange(e);
                        // and do something about e
                        setshowError(false);
                        formik.values.noteEvaluation = 0;
                        formik.values.tauxEvaluation = 0;
                      }}
                      onBlur={formik.handleBlur}
                      custom
                      size="sm"
                      name="evaluationStagePK.codeEvaluation"
                    >
                      <option style={{ display: "none" }}>
                        
                        ---- Select an option ----
                      </option>
                      {evaluationsTypes?.map((e, i) => (
                        <option value={e[1]} key={i}>
                          {e[0]}
                        </option>
                      ))}
                      {evaluationsTypes}
                    </CSelect>
                    {formik.errors.evaluationStagePK &&
                    formik.errors.evaluationStagePK.codeEvaluation &&
                    formik.touched.evaluationStagePK.codeEvaluation ? (
                      <p style={{ color: "red" }}>
                        {formik.errors.evaluationStagePK.codeEvaluation}
                      </p>
                    ) : (
                      ""
                    )}
                    <br /> <br></br>
                    {showError && (
                      <CAlert color="danger">
                        Vous avez déja saisi cette note ,choisir un autre type
                        s'il vous plait
                      </CAlert>
                    )}
                  </CCol>
                </CFormGroup>
                <CFormGroup>
                  <CLabel>
                    <b>Note :</b>
                  </CLabel>
                  <CInput
                    value={formik.values.noteEvaluation}
                    onChange={formik.handleChange}
                    type="number"
                    name="noteEvaluation"
                    placeholder="saisir une note ..."
                  />
                  {formik.errors.noteEvaluation &&
                    formik.touched.noteEvaluation && (
                      <p style={{ color: "red" }}>
                        {formik.errors.noteEvaluation}
                      </p>
                    )}
                </CFormGroup>
                <CFormGroup>
                  <CLabel>
                    
                    <b>Taux :</b>
                  </CLabel>
                  <CInput
                    name="tauxEvaluation"
                    value={formik.values.tauxEvaluation}
                    onChange={formik.handleChange}
                    type="number"
                    placeholder="Saisir un taux ..."
                  />
                  {formik.errors.tauxEvaluation &&
                    formik.touched.tauxEvaluation && (
                      <p style={{ color: "red" }}>
                        {formik.errors.tauxEvaluation}
                      </p>
                    )}
                </CFormGroup>
                <CButton
                  className="float-right"
                  name="btnsubmit"
                  type="submit"
                  color="danger"
                >
                  
                  Enregister
                </CButton>
              </CForm>
            </CCardBody>
          </CCard>
        </CCol>
      </div>
    </>
  );
};

export default AddEvaluation;
