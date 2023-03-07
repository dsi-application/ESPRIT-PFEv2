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
import { useFormik } from "formik";
import { useHistory } from "react-router";
import { selectSelectedFiche } from "../../../redux/slices/StudentSlice";
import { queryApi } from "../../../utils/queryApi";
import {
  unselectVisit,
  updateVisitDate,
} from "../../../redux/slices/VisteSlice";
import moment from "moment";
import { selectetudiant } from "../../../redux/slices/FichePFESlice";

const UpdateVisitDate = () => {
  const Etu = useSelector(selectetudiant);
  const [showError, setshowError] = useState(false);
  const [showLoader, setShowLoader] = useState(false);
  const dispatch = useDispatch();
  const history = useHistory();
  const [error, setError] = useState({ visible: false, message: "" });
  const Fiche = useSelector(selectSelectedFiche);
  useEffect(() => {
    if (!Fiche) history.replace("/FichePFEDetails");
  }, [Fiche, history]);
  const formik = useFormik({
    initialValues: {
      visiteStagiairePK: {
        fichePFEPK: {
          idEt: Fiche.idFichePFE.idEt,
          dateDepotFiche: Fiche.idFichePFE.dateDepotFiche,
        },
        dateVisite: Fiche.visiteStagiaire.visiteStagiairePK.dateVisite,
      },
    },

    //validationSchema: validationSchema,
    onSubmit: async (values) => {
      const date = values.visiteStagiairePK.dateVisite;
      const dateFormat = "DD-MM-YYYY";
      const toDateFormat = moment(new Date(date)).format(dateFormat);
      if (
        values.visiteStagiairePK.dateVisite !== "" &&
        moment(toDateFormat, dateFormat, true).isValid()
      ) {
        setShowLoader(true);
        const [res, err] = await queryApi(
          "encadrement/updateVisiteDate?newDate=" +
            values.visiteStagiairePK.dateVisite +
            "&idET=" +
            Fiche.idFichePFE.idEt +
            "&dateFiche=" +
            Fiche.idFichePFE.dateDepotFiche +
            "&dateVisite=" +
            Fiche.visiteStagiaire.visiteStagiairePK.dateVisite,
          values,
          "PUT",
          false
        );
        let object3 = [];
        object3.push({
          visiteStagiairePK: {
            fichePFEPK: {
              idEt: Fiche.idFichePFE.idEt,
              dateDepotFiche: Fiche.idFichePFE.dateDepotFiche,
            },
            dateVisite: Fiche.visiteStagiaire.visiteStagiairePK.dateVisite,
          },
        });
        if (err) {
          setShowLoader(false);
          setError({
            visible: true,
            message: JSON.stringify(err.errors, null, 2),
          });
        } else {
          object3.push(res);
          dispatch(updateVisitDate(object3));
          dispatch(unselectVisit(object3[1]));
          if (showError === true) setshowError(false);

          history.push("/VisitIntern");
        }
      } else {
        setshowError(true);
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
                  Modification des informations du visite de l'étudiant avec un
                  ID <b>{Fiche.idFichePFE.idEt} </b>
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
                          * Date invalide ou Champ vide ! veuillez saisir une
                          date valide
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
                </CFormGroup>
              </CForm>
            </CCardBody>
          </CCard>
        </CCol>
      </div>
    </>
  );
};

export default UpdateVisitDate;
