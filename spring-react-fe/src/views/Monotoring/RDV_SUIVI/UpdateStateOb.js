import React, { useEffect, useState } from "react";
import {
  CAlert,
  CButton,
  CCard,
  CCardBody,
  CCardHeader,
  CCol,
  CForm,
  CFormGroup,
  CLabel,
  CRow,
  CSelect,
  CSpinner,
} from "@coreui/react";
import { CKEditor } from "@ckeditor/ckeditor5-react";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import { useDispatch, useSelector } from "react-redux";
import * as Yup from "yup";
import { useFormik } from "formik";
import { useHistory } from "react-router";
import { selectSelectedFiche } from "../../../redux/slices/StudentSlice";
import { queryApi } from "../../../utils/queryApi";
import {
  selectSelectedrdv,
  updateRdvPeda,
} from "../../../redux/slices/RDVPedaSlice";
import { selectetudiant } from "../../../redux/slices/FichePFESlice";
import {
  fetchRendezVousSuiviTypes,
  selectRendezVousSuiviTypes,
} from "../../../redux/slices/ListingSlice";
const validationSchemaSuivi = Yup.object().shape({
  etat: Yup.string().required("* Champ obligatoire !"),
  observation: Yup.string()
    .max(2000, "Ne pas dépasser 2000 caractéres !")
    .required("* Champ obligatoire !"),
});
const UpdateStateOb = () => {
  const Etu = useSelector(selectetudiant);
  const [showLoader, setShowLoader] = useState(false);
  const dispatch = useDispatch();
  const history = useHistory();
  const [error, setError] = useState({ visible: false, message: "" });
  const Fiche = useSelector(selectSelectedFiche);
  const RDV = useSelector(selectSelectedrdv);
  const [RDVSuivisTypes, errRDVTypes] = useSelector(selectRendezVousSuiviTypes);

  useEffect(() => {
    if (RDVSuivisTypes === null || RDVSuivisTypes.length === 0) {
      dispatch(fetchRendezVousSuiviTypes());
    }
    if (!Fiche) history.replace("/FichePFEDetails");
  }, [Fiche, dispatch, history]);

  const formikModifRDVInfo = useFormik({
    initialValues: {
      rdvSuiviStagePK: {
        fichePFEPK: {
          idEt: Fiche.idFichePFE.idEt,
          dateDepotFiche: Fiche.idFichePFE.dateDepotFiche,
        },
        dateSaisieRendezVous: RDV.rdvSuiviStagePK.dateSaisieRendezVous,
      },
      dateRendezVous: RDV.dateRendezVous,
      etat: RDV.etat,
      heureDebut: RDV.heureDebut,
      lieuRDV: RDV.lieuRDV,
      observation: RDV.observation,
      typeRDV: RDV.typeRDV,
    },
    validationSchema: validationSchemaSuivi,
    onSubmit: async (values) => {
      setShowLoader(true);
      const [res, err] = await queryApi(
        "encadrement/updateRdvSuiviStage?idET=" +
          Fiche.idFichePFE.idEt +
          "&dateFiche=" +
          Fiche.idFichePFE.dateDepotFiche +
          "&dateSaisiRDV=" +
          RDV.rdvSuiviStagePK.dateSaisieRendezVous,
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
        dispatch(updateRdvPeda(res));
        sessionStorage.setItem("DataIndex", 2);
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
                {" "}
                <CCol xs="12" sm="6" md="6">
                  Modification des informations d'un rendez vous de suivi de
                  l'étudiant avec un ID <b>{Fiche.idFichePFE.idEt} </b>
                </CCol>
                <CCol xs="12" sm="6" md="6">
                  <h4 className="float-right">
                    <b>
                      {Etu.prenomet} {Etu.nomet} &nbsp;&nbsp; {Etu.classe}
                    </b>
                  </h4>{" "}
                  <br></br> <br></br>
                  <h6 className="float-right"> {Etu.adressemailesp}</h6>
                </CCol>
              </CRow>
            </CCardHeader>

            <CCardBody>
              <CForm onSubmit={formikModifRDVInfo.handleSubmit}>
                <CFormGroup>
                  {" "}
                  {error.visible && <p>{error.message}</p>}
                </CFormGroup>
                <CAlert color="success">
                  {" "}
                  Vous pouvez saisir d'autres informations{" "}
                </CAlert>
                <CFormGroup row>
                  <CCol md="3">
                    <CLabel htmlFor="selectSm">
                      <b>Type Rendez-Vous</b>
                    </CLabel>
                  </CCol>
                  <CCol xs="12" md="9">
                    <CSelect
                      value={formikModifRDVInfo.values.etat}
                      onChange={formikModifRDVInfo.handleChange}
                      onBlur={formikModifRDVInfo.handleBlur}
                      custom
                      size="sm"
                      name="etat"
                    >
                      <option style={{ display: "none" }}>
                        {" "}
                        ---- Choisir une option ----{" "}
                      </option>
                      {RDVSuivisTypes?.map((e, i) => (
                        <option value={e[1]} key={i}>
                          {e[0]}
                        </option>
                      ))}
                    </CSelect>

                    {formikModifRDVInfo.errors.etat &&
                      formikModifRDVInfo.touched.etat && (
                        <p style={{ color: "red" }}>
                          {formikModifRDVInfo.errors.etat}
                        </p>
                      )}

                    <br />
                  </CCol>
                </CFormGroup>
                <CFormGroup row>
                  <CCol md="3">
                    <CLabel>
                      <b>Observation :</b>
                    </CLabel>
                  </CCol>
                  <CCol xs="12" md="9">
                    <CKEditor
                      editor={ClassicEditor}
                      config={{
                        toolbar: [
                          "heading",
                          "|",
                          "bold",
                          "italic",
                          "link",
                          "bulletedList",
                          "numberedList",
                          "blockQuote",
                        ],
                      }}
                      data={formikModifRDVInfo.values.observation}
                      onReady={(editor) => {
                        // You can store the "editor" and use when it is needed.
                        // console.log("Editor is ready to use!", editor);
                      }}
                      onChange={(event, editor) => {
                        const data = editor.getData();
                        formikModifRDVInfo.setFieldValue("observation", data);
                      }}
                      onBlur={(event, editor) => {
                        // console.log("Blur.", editor);
                      }}
                      onFocus={(event, editor) => {
                        // console.log("Focus.", editor);
                      }}
                    />
                    {formikModifRDVInfo.errors.observation &&
                      formikModifRDVInfo.touched.observation && (
                        <p style={{ color: "red" }}>
                          {formikModifRDVInfo.errors.observation}
                        </p>
                      )}

                    <br />
                  </CCol>
                </CFormGroup>
                <CButton
                  disabled={showLoader}
                  className="float-right"
                  name="btnsubmit"
                  type="submit"
                  color="danger"
                >
                  {showLoader && <CSpinner grow size="sm" />}
                  Enregister les changements
                </CButton>
                <br></br> <br></br>
                {showLoader && (
                  <CAlert color="danger" closeButton>
                    Attendre un petit peu ...
                  </CAlert>
                )}
              </CForm>
            </CCardBody>
          </CCard>
        </CCol>
      </div>
    </>
  );
};

export default UpdateStateOb;
