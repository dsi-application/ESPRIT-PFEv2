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
  CRow,
  CSpinner,
} from "@coreui/react";
import { useDispatch, useSelector } from "react-redux";
import * as Yup from "yup";
import { useFormik } from "formik";
import { queryApi } from "../../utils/queryApi";
import { useHistory } from "react-router";
import { getEvalType } from "../Monotoring/FichePFEDetails";
import {
  updateEvaluation,
  selectSelectedEvaluation,
} from "../../redux/slices/EvaluationSlice";
import { selectetudiant } from "../../redux/slices/FichePFESlice";
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
});
const UpdateEvaluation = () => {
  const Etu = useSelector(selectetudiant);
  const history = useHistory();
  const dispatch = useDispatch();
  const [showLoader, setShowLoader] = useState(false);
  const SelectedEval = useSelector(selectSelectedEvaluation);
  const [error, setError] = useState({ visible: false, message: "" });

  useEffect(() => {
    if (!SelectedEval) history.replace("/PFESheetDetails");
  }, [SelectedEval, history]);
  const formikModif = useFormik({
    initialValues: {
      noteEvaluation: SelectedEval.noteEvaluation,
      tauxEvaluation: SelectedEval.tauxEvaluation,
    },
    validationSchema: validationSchema,
    onSubmit: async (values) => {
      setShowLoader(true);
      const [res, err] = await queryApi(
        "encadrement/updateEvalEncadrantPedaNote?idET=" +
          SelectedEval.evaluationStagePK.fichePFEPK.idEt +
          "&dateFiche=" +
          SelectedEval.evaluationStagePK.fichePFEPK.dateDepotFiche +
          "&code=" +
          SelectedEval.evaluationStagePK.codeEvaluation,
        values,
        "PUT",
        false
      );

      if (err) {
        setShowLoader(false);
        setError({
          visible: true,
          message: JSON.stringify(err.errors, null, 2),
        });
      } else {
        dispatch(updateEvaluation(res));
        sessionStorage.setItem("DataIndex", 4);
        history.push("/PFESheetDetails");
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
                  <h5>Modification de la note </h5>
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
              <CForm onSubmit={formikModif.handleSubmit}>
                <CFormGroup>
                  
                  {error.visible && <p>{error.message}</p>}
                </CFormGroup>
                <CFormGroup row>
                  <CCol md="4">
                    <CLabel htmlFor="selectSm">
                      <b>Type d'évaluation: </b>
                    </CLabel>
                    <p style={{ color: "#ed1c24" }}>
                      
                      {getEvalType(
                        SelectedEval.evaluationStagePK.codeEvaluation
                      )}
                    </p>
                  </CCol>
                </CFormGroup>
                <CFormGroup>
                  <CLabel>
                    <b>Note :</b>
                  </CLabel>
                  <CInput
                    value={formikModif.values.noteEvaluation}
                    onChange={formikModif.handleChange}
                    type="number"
                    name="noteEvaluation"
                    placeholder="Saisir une note ..."
                  />
                  {formikModif.errors.noteEvaluation &&
                    formikModif.touched.noteEvaluation && (
                      <p style={{ color: "red" }}>
                        {formikModif.errors.noteEvaluation}
                      </p>
                    )}
                </CFormGroup>
                <CFormGroup>
                  <CLabel>
                    
                    <b>Taux :</b>
                  </CLabel>
                  <CInput
                    name="tauxEvaluation"
                    value={formikModif.values.tauxEvaluation}
                    onChange={formikModif.handleChange}
                    type="number"
                    placeholder="Saisir un taux ..."
                  />
                  {formikModif.errors.tauxEvaluation &&
                    formikModif.touched.tauxEvaluation && (
                      <p style={{ color: "red" }}>
                        {formikModif.errors.tauxEvaluation}
                      </p>
                    )}
                </CFormGroup>

                <CButton
                  className="float-right"
                  type="submit"
                  disabled={showLoader}
                  color="danger"
                >
                  
                  {showLoader && <CSpinner size="sm" variant="grow" />}
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

export default UpdateEvaluation;
