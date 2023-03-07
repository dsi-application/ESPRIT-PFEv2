import React, { useState } from "react";
import { useSelector } from "react-redux";

import "moment/locale/fr";
import {
  CCard,
  CCardBody,
  CCardGroup,
  CCardHeader,
  CCol,
  CRow,
  CSpinner,
  CWidgetProgressIcon,
} from "@coreui/react";
import CIcon from "@coreui/icons-react";
import {
  selectEnseignantStat,
  selectEnseignantStat2,
} from "../../redux/slices/StatSlice";
import { CChartBar } from "@coreui/react-chartjs/lib";
import Tour from "reactour";
import LocalLibrary from "@material-ui/icons/LocalLibrary";
import IconButton from "@material-ui/core/IconButton";
import Tooltip from "@material-ui/core/Tooltip";
const steps = [
  {
    selector: '[data-tut="reactour__iso"]',
    content: `Içi, Vous allez trouver quelques statistiques de vos encadrements tout au long de votre parcous à ESPRIT.`,
  },
  {
    selector: '[data-tut="reactour__logo"]',
    content: `Et de même dans cette partie...`,
  },
];

const TeacherHome = () => {
  const [isTourOpen, setIsTourOpen] = useState(false);
  const [EnseignantStat, err] = useSelector(selectEnseignantStat);
  const [EnseignantStat2, err2] = useSelector(selectEnseignantStat2);

  const EnseignantStatStatus = useSelector(
    (state) => state.persistedReducer.stat.EnseignantStatStatus
  );
  const EnseignantStat2Status = useSelector(
    (state) => state.persistedReducer.stat.EnseignantStat2Status
  );

  return (
    <>
      <CRow>
        <CCol sm="6" md="6">
          <CCard>
            <CCardHeader data-tut="reactour__iso">
              Nombre d'encadrements
            </CCardHeader>
            <Tour
              steps={steps}
              isOpen={isTourOpen}
              onAfterOpen={(target) =>
                (document.body.style.overflowY = "hidden")
              }
              onBeforeClose={(target) =>
                (document.body.style.overflowY = "auto")
              }
              onRequestClose={() => setIsTourOpen(false)}
            />

            <br></br>
            {EnseignantStat2Status === "loading" ||
            EnseignantStat2Status === "noData" ? (
              <>
                <div style={{ textAlign: "center" }}>
                  <br/><br/>
                  <CSpinner color="danger" grow size="lg" />

                  <br/><br/>
                  <b>Veuillez patienter le chargement des données ...</b>
                </div>
                <br></br>
              </>
            ) : EnseignantStat2 ? (
              <CCardBody>
                <CChartBar
                  type="bar"
                  datasets={[
                    {
                      label: "Encadrement",
                      backgroundColor: "#DB4437",
                      data: EnseignantStat2?.map((e) => e.nbr),
                    },
                  ]}
                  labels={EnseignantStat2?.map((e) => e.annee)}
                  options={{
                    tooltips: {
                      enabled: true,
                    },
                  }}
                />
              </CCardBody>
            ) : (
              <CCardBody>
                <b> Vous n'avez pas encore d'encadrement </b>
              </CCardBody>
            )}
          </CCard>
        </CCol>
        <CCol sm="6" md="6">
          <CCardGroup className="mb-4" data-tut="reactour__logo">
            
            <br></br>
            {EnseignantStatStatus === "loading" ||
            EnseignantStatStatus === "noData" ? (
              <>
                <div>
                  <CSpinner color="warning" grow size="lg" />
                  &nbsp;
                  <CSpinner color="danger" grow size="lg" />
                  &nbsp;
                  <CSpinner color="primary" grow size="lg" />
                  &nbsp;
                  <br></br>
                </div>
              </>
            ) : (
              <>
                <CWidgetProgressIcon
                  header={EnseignantStat[0].nombreEncadrementAllStudent}
                  text="Nombre Total d'encadrement"
                  color="gradient-danger"
                  inverse
                >
                  <CIcon name="cil-chartPie" height="36" />
                </CWidgetProgressIcon>
                <CWidgetProgressIcon
                  header={EnseignantStat[0].nombreEncadrementAchevé}
                  text="Encadrements achevés"
                  color="gradient-dark"
                  inverse
                >
                  <CIcon name="cil-userFollow" height="36" />
                </CWidgetProgressIcon>
              </>
            )}
            <br></br>
          </CCardGroup>
        </CCol>
      </CRow>
      <br></br>
    </>
  );
};

export default TeacherHome;
