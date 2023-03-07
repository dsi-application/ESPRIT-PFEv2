import React from "react";
import { useSelector } from "react-redux";

import "moment/locale/fr";
import {
  CCardGroup,
  CCol,
  CRow,
  CSpinner,
  CWidgetProgressIcon,
} from "@coreui/react";
import CIcon from "@coreui/icons-react";
import { selectServiceStat } from "../../redux/slices/StatSlice";

const HomeService = () => {
  const [ServiceStat, err] = useSelector(selectServiceStat);
  const ServiceStatStatus = useSelector(
    (state) => state.persistedReducer.stat.ServiceStatStatus
  );
  return (
    <>
      {ServiceStatStatus === "loading" || ServiceStatStatus === "noData" ? (
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
          <CRow>
            <CCol sm="6" md="9">
              <CCardGroup>
                {ServiceStat ? (
                  ServiceStat[0] ? (
                    <>
                      <CWidgetProgressIcon
                        header={ServiceStat[0].nombreAllConvention}
                        text="Total Conventions"
                        color="gradient-info"
                        inverse
                      >
                        <CIcon name="cil-people" height="36" />
                      </CWidgetProgressIcon>
                      <CWidgetProgressIcon
                        header={ServiceStat[0].nombreConventionTraite}
                        text="Convention TRAITEE"
                        color="gradient-success"
                        inverse
                      >
                        <CIcon name="cil-speedometer" height="36" />
                      </CWidgetProgressIcon>
                      <CWidgetProgressIcon
                        header={ServiceStat[0].nombreConventionNontraite}
                        text="Convention Non TRAITEE"
                        color="gradient-warning"
                        inverse
                      >
                        <CIcon name="cil-chartPie" height="36" />
                      </CWidgetProgressIcon>
                      <CWidgetProgressIcon
                        header={ServiceStat[0].nombreConventionAnnuler}
                        text="Convention ANNULEE"
                        color="gradient-danger"
                        inverse
                      >
                        <CIcon name="cil-speedometer" height="36" />
                      </CWidgetProgressIcon>
                    </>
                  ) : (
                    <></>
                  )
                ) : (
                  <></>
                )}
              </CCardGroup>
            </CCol>
          </CRow>
          <br></br> <br></br>
          <CRow>
            <CCol sm="6" md="9">
              <CCardGroup className="mb-4">
                {ServiceStat ? (
                  <>
                    <CWidgetProgressIcon
                      header={ServiceStat[0].nombreAllAvenant}
                      text="Total Avenants"
                      color="gradient-info"
                      inverse
                    >
                      <CIcon name="cil-people" height="36" />
                    </CWidgetProgressIcon>

                    <CWidgetProgressIcon
                      header={ServiceStat[0].nombreAvenantNonTraite}
                      text="Avenant Non TRAITE"
                      color="gradient-danger"
                      inverse
                    >
                      <CIcon name="cil-userFollow" height="36" />
                    </CWidgetProgressIcon>
                    <CWidgetProgressIcon
                      header={ServiceStat[0].nombreAvenantTraite}
                      text="Avenant TRAITE"
                      color="gradient-success"
                      inverse
                    >
                      <CIcon name="cil-basket" height="36" />
                    </CWidgetProgressIcon>
                  </>
                ) : (
                  <></>
                )}
              </CCardGroup>
            </CCol>
          </CRow>
        </>
      )}
    </>
  );
};

export default HomeService;
