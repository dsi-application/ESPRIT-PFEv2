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
  CInput,
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
import { fetchVisits, selectSelectedVisit, unselectVisit, updateVisit } from "../../../redux/slices/VisteSlice";
import { selectetudiant } from "../../../redux/slices/FichePFESlice";
import {
  fetchEntreprises,
  selectEntreprises,
} from "../../../redux/slices/RDVPedaSlice";
import moment from "moment";
import { selectVisitesTypes } from "../../../redux/slices/ListingSlice";

const validationSchema = Yup.object().shape({
  observation: Yup.string()
      .max(1000, "Ne pas dépasser 1000 caractéres !")
      .required("* Champ obligatoire !"),
  lieuVisite: Yup.string()
      .required("* Champ obligatoire !"),
  typeVisite: Yup.string().required("* Champ obligatoire !"),
  heureDebut: Yup.string().required("* Champ obligatoire !"),
  heureFin: Yup.string().required("* Champ obligatoire !"),

});

const UpdateVisit = () => {
  const [VisiteTypes, errVisiteTypes] = useSelector(selectVisitesTypes);
  const [showError2, setshowError2] = useState(false);
  const [ent, errent] = useSelector(selectEntreprises);
  const Etu = useSelector(selectetudiant);
  const [showError, setshowError] = useState(false);
  const [showLoader, setShowLoader] = useState(false);
  const dispatch = useDispatch();
  const history = useHistory();
  const [error, setError] = useState({ visible: false, message: "" });
  const Fiche = useSelector(selectSelectedFiche);
  const Visite = useSelector(selectSelectedVisit);
  // console.log('ent===',ent)
  useEffect(() => {

    if (ent.length === 0) {
      dispatch(fetchEntreprises(Fiche.idFichePFE.idEt));
    }
    if (!Fiche) history.replace("/FichePFEDetails");
  }, [Fiche, history, dispatch]);
  const formik = useFormik({
    initialValues: {
      visiteStagiairePK: {
        fichePFEPK: {
          idEt: Visite.visiteStagiairePK.fichePFEPK.idEt,
          dateDepotFiche: Visite.visiteStagiairePK.fichePFEPK.dateDepotFiche,
        },
        dateVisite: Visite.visiteStagiairePK.dateVisite,
      },
      typeVisite: Visite.typeVisite,
      lieuVisite: Visite.lieuVisite,
      heureDebut: Visite.heureDebut,
      heureFin: Visite.heureFin,
      observation: Visite.observation,
    },
    validationSchema: validationSchema,
    onSubmit: async (values) => {
      const date = values.visiteStagiairePK.dateVisite;
      const dateFormat = "DD-MM-YYYY";
      const toDateFormat = moment(new Date(date)).format(dateFormat);
      if (
          values.visiteStagiairePK.dateVisite !== "" &&
          moment(toDateFormat, dateFormat, true).isValid() &&
          values.heureDebut < values.heureFin
      ) {
        setshowError(false);
        setShowLoader(true);
        const [res, err] = await queryApi(
            "encadrement/updateVisiteStagiaire?idET=" +
            Visite.visiteStagiairePK.fichePFEPK.idEt +
            "&dateFiche=" +
            Visite.visiteStagiairePK.fichePFEPK.dateDepotFiche +
            "&dateVisite=" +

            moment(Visite.visiteStagiairePK.dateVisite)
                .format("YYYY-MM-DD")

            ,
            values,
            "PUT",
            false
        );
        // console.log('tesult',Visite.visiteStagiairePK.dateVisite)
        // console.log('tesult2', moment(Visite.visiteStagiairePK.dateVisite).format("YYYY-MM-DD"))
        let object3 = [];
        object3.push({
          visiteStagiairePK: {
            fichePFEPK: {
              idEt:   Visite.visiteStagiairePK.fichePFEPK.idEt ,
              dateDepotFiche: Visite.visiteStagiairePK.fichePFEPK.dateDepotFiche,
            },
            dateVisite:  Visite.visiteStagiairePK.dateVisite,

          },

        });
        if (err) {
          setShowLoader(false);
          setError({
            visible: true,
            message: JSON.stringify(err.errors, null, 2),
          });
        } else {
          //console.log('res===',res)
          object3.push(res);
          dispatch(updateVisit(object3));

          dispatch(fetchVisits());
          sessionStorage.setItem("DataIndex", 3);
          history.push("/PFESheetDetails");
        }
      }
      else if (
          (values.visiteStagiairePK.dateVisite === "" &&
              !moment(toDateFormat, dateFormat, true).isValid()) ||
          values.heureDebut > values.heureFin
      ) {

        values.visiteStagiairePK.dateVisite === "" &&
        !moment(toDateFormat, dateFormat, true).isValid()
            ? setshowError(true)
            : setshowError(false);
        values.heureDebut > values.heureFin
            ? setshowError2(true)
            : setshowError2(false);
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
                    <CRow>
                      <CCol xs="12">&nbsp;
                        <span style={{color: "#b30000", fontSize: "13px", fontWeight: "bold"}}>
                          Modification des informations du visite de l'étudiant avec l'ID </span> &nbsp; {Fiche.idFichePFE.idEt}
                      </CCol>
                    </CRow>
                  </CCol>
                  <CCol xs="12" sm="6" md="6">
                    <h6 className="float-right">
                      <b>
                        {Etu.prenomet} {Etu.nomet} &nbsp;&nbsp; {Etu.classe}
                      </b>
                    </h6>
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
                  <CAlert color="info">
                    Vous pouvez saisir d'autres informations. &nbsp; Si non, veuillez enregistrer seulement.
                  </CAlert>
                  <CFormGroup row>
                    <CCol md="3">
                      <CLabel htmlFor="selectSm">
                        <b>Type du visite</b>
                      </CLabel>
                    </CCol>
                    <CCol xs="12" md="9">
                      <CSelect
                          value={formik.values.typeVisite}
                          onChange={formik.handleChange}
                          onBlur={formik.handleBlur}
                          custom
                          size="sm"
                          name="typeVisite"
                      >
                        <option style={{ display: "none" }}>
                          
                          ---- Choisir une option----
                        </option>
                        {VisiteTypes?.map((e, i) => (
                            <option value={e[1]} key={i}>
                              {e[0]}
                            </option>
                        ))}
                      </CSelect>
                      {formik.errors.typeVisite && formik.touched.typeVisite && (
                          <p style={{ color: "red" }}>{formik.errors.typeVisite}</p>
                      )}
                      <br />
                    </CCol>
                  </CFormGroup>
                  <CFormGroup row>
                    <CCol md="3">
                      <CLabel>
                        <b>Lieu :</b>
                      </CLabel>
                    </CCol>
                    <CCol xs="12" md="9">
                      <CSelect
                          value={formik.values.lieuVisite}
                          onChange={formik.handleChange}
                          onBlur={formik.handleBlur}
                          custom
                          size="sm"
                          name="lieuVisite"
                      >
                        <option style={{ display: "none" }}>
                          
                          ---- Choisir une option ----
                        </option>
                        {ent.length &&
                        ent.map((e, i) => (
                            <option value={e.address} key={i}>
                              {e.address}
                            </option>
                        ))}
                      </CSelect>
                      {formik.errors.lieuVisite && formik.touched.lieuVisite && (
                          <p style={{ color: "red" }}>{formik.errors.lieuVisite}</p>
                      )}
                    </CCol>
                  </CFormGroup>
                  <CFormGroup row>
                    <CCol md="3">
                      <CLabel htmlFor="date-input">
                        
                        <b>Date du visite :</b>
                      </CLabel>
                    </CCol>
                    <CCol xs="12" md="9">
                      <CInput
                          value={moment(
                              formik.values.visiteStagiairePK.dateVisite
                          )
                              .format("yyyy-MM-DD")
                              .toString()}
                          onChange={formik.handleChange}
                          type="date"
                          name="visiteStagiairePK.dateVisite"
                          placeholder="Visite Date"
                      />
                      {showError && (
                          <p style={{ color: "red" }}>
                            * Date invalide ou Champ vide ! veuillez saisir une date
                            valide
                          </p>
                      )}
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
                          data={formik.values.observation}
                          onReady={(editor) => {
                            // You can store the "editor" and use when it is needed.
                            //console.log("Editor is ready to use!", editor);
                          }}
                          onChange={(event, editor) => {
                            const data = editor.getData();
                            formik.setFieldValue("observation", data);
                          }}
                          onBlur={(event, editor) => {
                            //console.log("Blur.", editor);
                          }}
                          onFocus={(event, editor) => {
                            //console.log("Focus.", editor);
                          }}
                      />

                      {formik.errors.observation &&
                      formik.touched.observation && (
                          <p style={{ color: "red" }}>
                            {formik.errors.observation}
                          </p>
                      )}
                    </CCol>
                  </CFormGroup>
                  <CFormGroup row>
                    <CCol md="3">
                      <CLabel htmlFor="date-input">
                        
                        <b>Heure début:</b>
                      </CLabel>
                    </CCol>
                    <CCol xs="12" md="9">
                      <CInput
                          value={formik.values.heureDebut}
                          onChange={formik.handleChange}
                          type="time"
                          name="heureDebut"
                          placeholder="Visite Start time"
                      />
                      {formik.errors.heureDebut && formik.touched.heureDebut && (
                          <p style={{ color: "red" }}>{formik.errors.heureDebut}</p>
                      )}
                      {showError2 && (
                          <p style={{ color: "red" }}>
                            * L'heure du début doit être inférieure à l'heure de la
                            fin.
                          </p>
                      )}
                    </CCol>
                  </CFormGroup>
                  <CFormGroup row>
                    <p></p>
                    <CCol md="3">
                      <CLabel htmlFor="date-input">
                        
                        <b>Heure fin : </b>
                      </CLabel>
                    </CCol>
                    <CCol xs="12" md="9">
                      <CInput
                          value={formik.values.heureFin}
                          onChange={formik.handleChange}
                          type="time"
                          name="heureFin"
                          placeholder="Visite End time"
                      />
                      {formik.errors.heureFin && formik.touched.heureFin && (
                          <p style={{ color: "red" }}>{formik.errors.heureFin}</p>
                      )}
                      {showError2 && (
                          <p style={{ color: "red" }}>
                            * L'heure du début doit être inférieure à l'heure de la
                            fin.
                          </p>
                      )}
                    </CCol>
                  </CFormGroup>
                  <CButton
                      className="float-right"
                      disabled={showLoader}
                      name="btnsubmit"
                      type="submit"
                      color="danger"
                  >
                    {showLoader && <CSpinner grow size="sm" />}
                    Enregister les modifications
                  </CButton>
                  <br></br> <br></br>
                  {showLoader && (
                      <CAlert color="danger" closeButton>
                        Please wait a little ...
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

export default UpdateVisit;
