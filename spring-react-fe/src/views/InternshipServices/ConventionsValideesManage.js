import CIcon from "@coreui/icons-react";
import {freeSet} from '@coreui/icons';
import { useFormik } from "formik";
import GetApp from "@material-ui/icons/GetApp";

import * as Yup from "yup";

import {
  CBadge,
  CButton,
  CCard,
  CCardBody,
  CCol,
  CRow,
  CSpinner,
  CTooltip, CSelect, CForm, CFormGroup, CAlert
} from "@coreui/react";
import axios from "axios";
import moment from "moment";
import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import {
  deleteElem,
  fetchConventionsForRSS,
  selectConventions, selectConventionsForRSS, selectConventionsValidatedForRSS,
  selectNbrDemandesAnnulationConvention, selectNbrDemandesAnnulationConventionNotTreated,
  selectNbrDepositedConventions, selectNbrValidatedConventions, updateFichebydep
} from "../../redux/slices/ConventionSlice";
import { selectConvention } from "../../redux/slices/ConventionSlice";
import MUIDataTable from "mui-datatables";
import "moment/locale/fr";
import { getEtudiant } from "../../redux/slices/FichePFESlice";
import Tour from "reactour";
import LocalLibrary from "@material-ui/icons/LocalLibrary";
import IconButton from "@material-ui/core/IconButton";
import Tooltip from "@material-ui/core/Tooltip";
import Text from "antd/lib/typography/Text";
import { createMuiTheme } from "@material-ui/core";
import { MuiThemeProvider } from "@material-ui/core";
import TheSidebar from "../../containers/TheSidebar";
import {
  fetchActiveStudentTimelineStep,
  fetchConventionDetails,
  fetchDepotReportsDetails,
  fetchFichePFEDetails,
  fetchTimelineSteps, fetchVerifyAffectPEtoStudent
} from "../../redux/slices/MyStudentSlice";
import {queryApi} from "../../utils/queryApi";
import {
  fetchRefusedReports,
  fetchStudentToSTNStat,
  fetchUploadedReports,
  fetchValidatedReports
} from "../../redux/slices/DepotSlice";
import AuthService from "../../views/services/auth.service";

const validationSchema = Yup.object().shape({
  labelMonth: Yup.string().required("* Champ obligatoire !"),
});
const steps = [
  {
    selector: '[data-tut="reactour__1"]',
    content: `Içi, Vous allez trouver La 
    liste des conventions `,
  },
  {
    selector: '[data-tut="reactour__2"]',
    content: () => (
        <Text>
          Içi, vous pouvez interpréter l'état de la convention :
          <strong>DEPOSEE - TRAITEE - ANNULEE</strong>.
        </Text>
    ),
  },

  {
    selector: '[data-tut="reactour__3"]',
    content: () => (
        <Text>
          Pour voir les détails de la convention et la traiter cliquer sur ce
          bouton : <strong> Afficher détails</strong>.
        </Text>
    ),
  },
];



export const getBadge = (traiter) => {
  switch (traiter) {
    case "01":
      return "warning";
    case "02":
      return "success";

    default:
      return "danger";
  }
};
export const getEtat = (etat) => {
  switch (etat) {
    case "01":
      return "DEPOSEE";
    case "02":
      return "TRAITEE";

    default:
      return "ANNULEE";
  }
};
const ConventionsValideesManage = () => {
  const [isTourOpen, setIsTourOpen] = useState(false);

  //const [conventionsForRSS, err] = useSelector(selectConventionsValidatedForRSS);
  const [conventionsForRSS, setConventionsForRSS] = useState([]);

  const [responsive, setResponsive] = useState("vertical");
  const [tableBodyHeight, setTableBodyHeight] = useState("300");
  const [tableBodyMaxHeight, setTableBodyMaxHeight] = useState("");
  const [studentId, setStudentId] = useState(sessionStorage.getItem("studentId"));

  const ConventionsstatusForRSS = useSelector(
      (state) => state.persistedReducer.conventions.ConventionsstatusForRSS
  );





  const [showLoader, setShowLoader] = useState(false);
  const [error, setError] = useState({ visible: false, message: "" });
  const [danger, setDanger] = useState(false);
  const [date, setdate] = useState("");
  const [id, setid] = useState("");


  const dispatch = useDispatch();
  const columnsConventions = [
    {
      name: "idEt",
      label: "Identifiant",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "nomEt",
      label: "Nom & Prénom Étudiant",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "departEt",
      label: "Dépt",
      options: {
        filter: true,
        sort: true
      },
    },
    {
      name: "paysConvention",
      label: "Type",
      options: {
        filter: true,
        sort: true
      },
    },
    {
      name: "dateConvention",
      label: "Date dépôt Convention",
      options: {
        filter: true,
        sort: true
      },
    },
    {
      name: "dateDebut",
      label: "Date Début",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "dateFin",
      label: "Date Fin",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "",
      label: "",
      options: {
        filter: true,
        sort: true,
        customBodyRenderLite: (dataIndex) => {
          return (
              <>
                <td className="py-2" data-tut="reactour__3">
                  <Link to={"/ConventionDetails"}>
                    <CButton
                        variant="outline"
                        color="primary"
                        size="sm"
                        onClick={() => onClickConv(conventionsForRSS[dataIndex])}
                    >
                      <CTooltip content=" Afficher Détails">
                        <CIcon name="cil-magnifying-glass"></CIcon>
                      </CTooltip>
                    </CButton>
                  </Link>
                  &nbsp;&nbsp;
                  {btnDownloadDisplay(conventionsForRSS[dataIndex])}
                  &nbsp;&nbsp;
                  {btnDownloadSignedConvDisplay(conventionsForRSS[dataIndex])}
                  &nbsp;&nbsp;
                  <Link to={"/modifyAgreementByRSS"}>
                    <CButton
                        variant="outline"
                        color="danger"
                        size="sm"
                        onClick={() => passStudentId(conventionsForRSS[dataIndex])}
                    >
                      <CTooltip content=" Mettre à Jour">
                        <CIcon name="cil-pencil"></CIcon>
                      </CTooltip>
                    </CButton>
                  </Link>

                </td>
              </>
          );
        },
      },
    },
  ];
  const theme = createMuiTheme({
    overrides: {
      MuiTableCell: {
        root: {
          padding: "0px 0px 0px 20px",
        },
      },
    },
  });
  const options = {
    rowsPerPage: 5,
    filter: true,
    filterType: "dropdown",
    responsive,
    tableBodyHeight,
    tableBodyMaxHeight,
    download: false,
    fixedSelectColumn: true,
    selectableRows: "none",
    selectableRowsHeader: false,
    enableNestedDataAccess: ".",
    textLabels: {
      body: {
        noMatch: "Désolé, aucune donnée correspondante n'a été trouvée",
        toolTip: "Sort",
        columnHeaderTooltip: (column) => `Sort for ${column.label}`,
      },
      pagination: {
        next: "Page suivante",
        previous: "Page précédente",
        rowsPerPage: "Lignes par page:",
        displayRows: "de",
      },
      toolbar: {
        search: "Chercher",
        downloadCsv: "Download CSV",
        print: "Télécharger",
        viewColumns: "Afficher les colonnes",
        filterTable: "Tableau des filtres",
      },
      filter: {
        all: "Tout",
        title: "FILTRES",
        reset: "RÉINITIALISER",
      },
      viewColumns: {
        title: "Afficher les colonnes",
        titleAria: "Afficher / masquer les colonnes du tableau",
      },
      selectedRows: {
        text: "row(s) selected",
        delete: "Delete",
        deleteAria: "Delete Selected Rows",
      },
    },
  };

  const onClickConv = (i) => {
    dispatch(getEtudiant(i.idEt));
    dispatch(selectConvention(i));
    //dispatch(selectConvention(i));
    document.body.style.overflowY = "auto";
  };

  const passStudentId = (i) => {
    sessionStorage.setItem("studentId", i.idEt);
    console.log('-------- 1005 SimpleFormExample.js 1 -------------> SARS 1: ' + i.idEt);
    console.log('-------- 1005 SimpleFormExample.js 2 -------------> SARS 2: ' + sessionStorage.getItem("studentId"));
  };


  const formik = useFormik({
    initialValues: {
      labelMonth: "",
      allCancelMotifs: ["Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre"]
    },
    validationSchema: validationSchema,
    onSubmit: async (values) => {

      let currentResponsableServiceStage = AuthService.getCurrentResponsableServiceStage();
      setConventionsForRSS([]);
      setShowLoader(true);

      console.log('-----------------1-> 0908 id RSS: ' + currentResponsableServiceStage.id)

      const [res, err] = await queryApi(
          "serviceStage/allConventionValidatedListByRSS?idRSS=" + currentResponsableServiceStage.id +
          "&labelMonth=" +
          values.labelMonth,
          {},
          "PUT",
          false
      );
      console.log("sars0508", res);

      if (err) {
        setShowLoader(false);
        console.log('-----------------1-> 0306')
        setError({
          visible: true,
          message: JSON.stringify(err.errors, null, 2),
        });
      } else {
        console.log('-----------------2-> 0508')
        setConventionsForRSS([]);
        setConventionsForRSS(res);
        /*dispatch(updateFichebydep(res));
        dispatch(deleteElem(res));
        dispatch(fetchUploadedReports());
        dispatch(fetchValidatedReports());
        dispatch(fetchRefusedReports());
        dispatch(fetchStudentToSTNStat());*/
        setDanger(false);
        setShowLoader(false);
      }
    },
  });

  const Download = (p) => {
    axios
        .get(
            `${process.env.REACT_APP_API_URL}` +
            "encadrement/downloadFD/" + p.idEt + "/" + p.dateConvention,

            { responseType: "blob" }
        )
        .then((response) => {
          //const filename =  response.headers.get('Content-Disposition').split('filename=')[1];
          let pathConv = p.pathConvention;

          const file = new Blob([response.data], { type: "application/pdf" });
          let url = window.URL.createObjectURL(file);

          let a = document.createElement("a");
          a.href = url;
          a.download = pathConv.substring(pathConv.lastIndexOf("/") + 1);
          // console.log(url);
          window.open(url);
          a.click();
        });
  };

  const DownloadSignedConv = (p) => {

    let encodedURL = encodeURIComponent(encodeURIComponent(p.pathSignedConvention));
    axios.get(`${process.env.REACT_APP_API_URL_STU}` + "downloadMyPDF/" + encodedURL, { responseType: "blob" })
        .then((response) => {
          //const filename =  response.headers.get('Content-Disposition').split('filename=')[1];
          let pathConv = p.pathConvention;

          const file = new Blob([response.data], { type: "application/pdf" });
          let url = window.URL.createObjectURL(file);

          let a = document.createElement("a");
          a.href = url;
          a.download = pathConv.substring(pathConv.lastIndexOf("/") + 1);
          // console.log(url);
          window.open(url);
          a.click();
        });
  };

  const btnDownloadDisplay = (c) => {
    if (c.traiter === "02" || c.traiter === "03") {
      return (
          <CButton variant="outline"
                   color="warning"
                   size="sm"
                   onClick={() => {Download(c);}}>
            <CTooltip content="Télécharger Convention">
              <CIcon name="cil-save"></CIcon>
            </CTooltip>
          </CButton>
      );
    } else {
      return (
          <CButton variant="outline" color="warning" size="sm" disabled>
            <CTooltip content="Télécharger Convention">
              <CIcon name="cil-save"></CIcon>
            </CTooltip>
          </CButton>
      );
    }
  };

  const btnDownloadSignedConvDisplay = (c) => {
    if (c.traiter === "02" && c.pathSignedConvention !== null) {
      return (
          <CButton variant="outline"
                   color="success"
                   size="sm"
                   onClick={() => {DownloadSignedConv(c);}}>
            <CTooltip content="Télécharger Convention Signée">
              <CIcon name="cil-save"></CIcon>
            </CTooltip>
          </CButton>
      );
    }
    else {
      return (
          <>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </>
      );
    }
  };

  const [nbDemandesAnnulationConvention, errnir] = useSelector(selectNbrDemandesAnnulationConventionNotTreated);
  const [nbDepositedConventions, errndc] = useSelector(selectNbrDepositedConventions);
  const [nbValidatedConventions, errnvc] = useSelector(selectNbrValidatedConventions);

  /*const loadDataOnlyOnce = () => {
    console.log('-----------------> HELLO 2 -- 1005')
    dispatch(fetchConventionsForRSS());
  };

  useEffect(() => {
    console.log('-----------------> HELLO 1 -- 1005')
    loadDataOnlyOnce(); // this will fire only on first render
    console.log('-----------------> HELLO 3 -- 1005')
  }, []);
*/
  return (
      <>

        <TheSidebar dataDAC={nbDemandesAnnulationConvention} dataDC={nbDepositedConventions} dataVC={nbValidatedConventions}/>

        <Tour
            steps={steps}
            isOpen={isTourOpen}
            onAfterOpen={(target) => (document.body.style.overflowY = "hidden")}
            onBeforeClose={(target) => (document.body.style.overflowY = "auto")}
            onRequestClose={() => setIsTourOpen(false)}
        />
        {ConventionsstatusForRSS === "loading" || ConventionsstatusForRSS === "noData" ? (
            <div>
              <CRow>
                <CCol md="12">
                  <center>
                    <br/><br/>
                    <span className="waitIcon" /> **
                    <br></br>
                  </center>
                </CCol>
              </CRow>
            </div>
        ) : (
            <>

              <CRow>
                <CCol>
                  <CCard data-tut="reactour__1">


                    <CRow>
                      <CCol xs="12">
                        <br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <span style={{color: "#b30000", fontSize: "14px", fontWeight: "bold"}}>
                      Liste des Conventions Validées</span>
                      </CCol>
                    </CRow>
                    <br/>

                    <CRow>
                      <CCol md="4"/>
                      <CCol md="4">
                        <CForm onSubmit={formik.handleSubmit}>
                          <CFormGroup>

                            {error.visible && <p>{error.message}</p>}
                          </CFormGroup>
                          <center>
                            Choisir le mois pour visualiser la liste correspondante :
                          </center>

                          <br/>
                          <CFormGroup row>
                            <CCol md="1"/>
                            <CCol md="10">
                              <CSelect  value={formik.values.labelMonth}
                                        onChange={formik.handleChange}
                                        onBlur={formik.handleBlur}
                                        custom
                                        size="sm"
                                        name="labelMonth">
                                <option style={{ display: "none" }}>
                                  ---- Choisir un mois ----
                                </option>
                                {formik.values.allCancelMotifs?.map((e, i) => (
                                    <option value={e} key={i}>
                                      {e}
                                    </option>
                                ))}
                              </CSelect>
                              {
                                formik.errors.labelMonth && formik.touched.labelMonth &&
                                <p style={{ color: "red" }}>{formik.errors.labelMonth}</p>
                              }
                              <br />
                            </CCol>
                            <CCol md="1"/>
                            <br/><br/>
                          </CFormGroup>
                          <center>
                            <CButton  color="danger" type="submit">
                              {showLoader && <CSpinner grow size="sm" />} &nbsp; Confirmer
                            </CButton>
                          </center>
                        </CForm>
                      </CCol>
                      <CCol md="4"/>
                    </CRow>


                    <br/><br/>
                    <CCardBody>
                      {conventionsForRSS ? (
                          <MuiThemeProvider theme={theme}>
                            <MUIDataTable
                                data={conventionsForRSS}
                                columns={columnsConventions}
                                options={options}
                            />
                          </MuiThemeProvider>
                      ) : (
                          <MuiThemeProvider theme={theme}>
                            <MUIDataTable
                                columns={columnsConventions}
                                options={options}
                            /> </MuiThemeProvider>
                      )}
                    </CCardBody>

                  </CCard>
                </CCol>
              </CRow>
              {conventionsForRSS ? (
                  ""
              ) : (
                  <></>
              )}
            </>
        )}
      </>
  );
};

export default ConventionsValideesManage;
