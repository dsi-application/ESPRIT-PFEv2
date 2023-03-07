import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";

import "moment/locale/fr";
import {
  CCardGroup,
  CCol,
  CRow,
  CSpinner,
  CWidgetProgressIcon,
} from "@coreui/react";
import CIcon from "@coreui/icons-react";
import { selectResponsableStat } from "../../redux/slices/StatSlice";

const HomeManager = () => {
  const dispatch = useDispatch();
  const [spinner, setSpinner] = useState(true);
  const [responsableStat, err] = useSelector(selectResponsableStat);
  const responsableStatStatus = useSelector(
    (state) => state.persistedReducer.stat.ResponsableStatStatus
  );
  return (
    <>
      <CRow>
        
        <CCol sm="6" md="10">
          {responsableStatStatus === "loading" ||
          responsableStatStatus === "noData" ? (
            <>
              <div style={{ textAlign: "center" }}>
                <br/><br/>
                <CSpinner color="danger" grow size="lg" />

                <br/><br/>
                <b>Veuillez patienter le chargement des données ...</b>
              </div>
              <br></br>
            </>
          ) : (
            <>
              <CCardGroup className="mb-4">
                {responsableStat[0] ? (
                  <>
                    <CWidgetProgressIcon
                      header={responsableStat[0].nombreAllFiche}
                      text="Nombre TOTAL Plan Travail"
                      color="gradient-info"
                      inverse
                    >
                      <CIcon name="cil-people" height="36" />
                    </CWidgetProgressIcon>
                    <CWidgetProgressIcon
                      header={responsableStat[0].nombreFicheNontraite}
                      text="Fiche NON TRAITEE"
                      color="gradient-danger"
                      inverse
                    >
                      <CIcon name="cil-speedometer" height="36" />
                    </CWidgetProgressIcon>
                    <CWidgetProgressIcon
                      header={responsableStat[0].nombreEtudiantSansFiche}
                      text="Etudiant Sans Plan Travail"
                      color="gradient-success"
                      inverse
                    >
                      <CIcon name="cil-userFollow" height="36" />
                    </CWidgetProgressIcon>
                    <CWidgetProgressIcon
                      header={responsableStat[0].nombreDemandeModification}
                      text="Demande Modification Fiche"
                      color="gradient-warning"
                      inverse
                    >
                      <CIcon name="cil-basket" height="36" />
                    </CWidgetProgressIcon>
                    <CWidgetProgressIcon
                      header={responsableStat[0].nombreDemandeAnnulation}
                      text="Demande Annulation Fiche"
                      color="gradient-primary"
                      inverse
                    >
                      <CIcon name="cil-chartPie" height="36" />
                    </CWidgetProgressIcon>
                  </>
                ) : (
                  <></>
                )}
              </CCardGroup>
            </>
          )}
        </CCol>
      </CRow>
    </>
  );
};

export default HomeManager;
