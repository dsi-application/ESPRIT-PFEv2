import CIcon from "@coreui/icons-react";
import {freeSet} from '@coreui/icons';

import {
  CBadge, CButton, CCard, CCardBody, CCol, CRow, CSpinner, CTooltip, CSelect, CForm, CFormGroup, CAlert
} from "@coreui/react";

import axios from "axios";
import moment from "moment";
import React, {useState, useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import {Link} from "react-router-dom";
import {
  fetchConventionsForRSS,
  selectConventions,
  selectConventionsForRSS,
  selectNbrDemandesAnnulationConvention,
  selectNbrDemandesAnnulationConventionNotTreated,
  selectNbrDepositedConventions,
  selectNbrValidatedConventions
} from "../../redux/slices/ConventionSlice";
import {selectConvention} from "../../redux/slices/ConventionSlice";
import MUIDataTable from "mui-datatables";
import "moment/locale/fr";
import {getEtudiant} from "../../redux/slices/FichePFESlice";
import Tour from "reactour";
import LocalLibrary from "@material-ui/icons/LocalLibrary";
import IconButton from "@material-ui/core/IconButton";
import Tooltip from "@material-ui/core/Tooltip";
import Text from "antd/lib/typography/Text";
import {createMuiTheme} from "@material-ui/core";
import {MuiThemeProvider} from "@material-ui/core";
import TheSidebar from "../../containers/TheSidebar";
import {
  fetchActiveStudentTimelineStep,
  fetchConventionDetails,
  fetchDepotReportsDetails,
  fetchFichePFEDetails,
  fetchTimelineSteps,
  fetchVerifyAffectPEtoStudent
} from "../../redux/slices/MyStudentSlice";
import {useFormik} from "formik";
import AuthService from "../services/auth.service";
import {queryApi} from "../../utils/queryApi";
import * as Yup from "yup";

const validationSchema = Yup.object().shape({
  yearLabel: Yup.string().required("* Année est un Champ obligatoire !."),
});

const steps = [{
  selector: '[data-tut="reactour__1"]', content: `Içi, Vous allez trouver La
    liste des conventions `,
}, {
  selector: '[data-tut="reactour__2"]', content: () => (<Text>
    Içi, vous pouvez interpréter l'état de la convention :
    <strong>DEPOSEE - TRAITEE - ANNULEE</strong>.
  </Text>),
},

  {
    selector: '[data-tut="reactour__3"]', content: () => (<Text>
      Pour voir les détails de la convention et la traiter cliquer sur ce
      bouton : <strong> Afficher détails</strong>.
    </Text>),
  },];

export const getBadge = (traiter) => {
  switch (traiter) {
    case "03":
      return "success";
    case "04":
      return "danger";
    default:
      return "dark";
  }
};

export const getEtat = (etat) => {
  switch (etat) {
    case "03":
      return "TRAITEE";
    case "04":
      return "EN ATTENTE";
    default:
      return "PAS ENCORE";
  }
};

const currentResponsableServiceStage = AuthService.getCurrentResponsableServiceStage();
const API_URL_RSS = process.env.REACT_APP_API_URL_RSS;

const ConventionsDemandesAnnulation = () => {
  const [isTourOpen, setIsTourOpen] = useState(false);
  // const [conventionsForRSS, err] = useSelector(selectConventionsForRSS);
  const [conventionsForRSS, setConventionsForRSS] = useState([]);

  const [responsive, setResponsive] = useState("vertical");
  const [tableBodyHeight, setTableBodyHeight] = useState("300");
  const [tableBodyMaxHeight, setTableBodyMaxHeight] = useState("");
  const [studentId, setStudentId] = useState(sessionStorage.getItem("studentId"));
  const [showLoader, setShowLoader] = useState(false);
  const [error, setError] = useState({visible: false, message: ""});
  const [danger, setDanger] = useState(false);
  const [allOpts, setAllOpts] = useState([]);

  const ConventionsstatusForRSS = useSelector((state) => state.persistedReducer.conventions.ConventionsstatusForRSS);
  const dispatch = useDispatch();
  const columnsConventions = [{
    name: "idEt", label: "Identifiant Étudiant", options: {
      filter: true, sort: true,
    },
  }, {
    name: "nomEt", label: "Nom & Prénom Étudiant", options: {
      filter: true, sort: true,
    },
  }, {
    name: "currentClasse", label: "Classe", options: {
      filter: true, sort: true,
    },
  }, {
    name: "departEt", label: "Option", options: {
      filter: true, sort: true
    },
  },
    {
      name: "dateDebut", label: "Date Début", options: {
        filter: true, sort: true,
      },
    }, {
      name: "dateFin", label: "Date Fin", options: {
        filter: true, sort: true,
      },
    }, {
      name: "traiter", label: "État", options: {
        filter: true, sort: true, customBodyRender: (e) => {
          return (<CBadge data-tut="reactour__2" color={getBadge(e)}>
            {getEtat(e)}
          </CBadge>);
        },
      },
    }, {
      name: "", label: "", options: {
        filter: true, sort: true, customBodyRenderLite: (dataIndex) => {
          return (<>
            <td className="py-2" data-tut="reactour__3">
              <Link to={"/DemandeAnnulationConventionDetails"}>
                <CButton
                  variant="outline"
                  color="dark"
                  size="sm"
                  onClick={() => onClickConv(conventionsForRSS[dataIndex])}
                >
                  <CTooltip content=" Afficher Détails">
                    <CIcon name="cil-magnifying-glass"></CIcon>
                  </CTooltip>
                </CButton>
              </Link>
            </td>
          </>);
        },
      },
    },];

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
      }, pagination: {
        next: "Page suivante", previous: "Page précédente", rowsPerPage: "Lignes par page:", displayRows: "de",
      }, toolbar: {
        search: "Chercher",
        downloadCsv: "Download CSV",
        print: "Télécharger",
        viewColumns: "Afficher les colonnes",
        filterTable: "Tableau des filtres",
      }, filter: {
        all: "Tout", title: "FILTRES", reset: "RÉINITIALISER",
      }, viewColumns: {
        title: "Afficher les colonnes", titleAria: "Afficher / masquer les colonnes du tableau",
      }, selectedRows: {
        text: "row(s) selected", delete: "Delete", deleteAria: "Delete Selected Rows",
      },
    },
  };

  const onClickConv = (i) => {

    console.log('-------- onClickConv1: ' + i);
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

  const Download = (p) => {
    axios
      .get(`${process.env.REACT_APP_API_URL}` + "encadrement/downloadFD/" + p.idEt + "/" + p.dateConvention,

        {responseType: "blob"})
      .then((response) => {
        //const filename =  response.headers.get('Content-Disposition').split('filename=')[1];
        let pathConv = p.pathConvention;

        const file = new Blob([response.data], {type: "application/pdf"});
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
      return (<CButton variant="outline"
                       color="danger"
                       size="sm"
                       onClick={() => {
                         Download(c);
                       }}>
        <CTooltip content="Télécharger Convention">
          <CIcon name="cil-save"></CIcon>
        </CTooltip>
      </CButton>);
    } else {
      return (<CButton variant="outline" color="danger" size="sm" disabled>
        <CTooltip content="Télécharger Convention">
          <CIcon name="cil-save"></CIcon>
        </CTooltip>
      </CButton>);
    }
  };

  const [nbDemandesAnnulationConvention, errnir] = useSelector(selectNbrDemandesAnnulationConventionNotTreated);
  const [nbDepositedConventions, errndc] = useSelector(selectNbrDepositedConventions);
  const [nbValidatedConventions, errnvc] = useSelector(selectNbrValidatedConventions);

  const loadDataOnlyOnce = () => {
    console.log('-----------------> HELLO 2 -- 1005')
    dispatch(fetchConventionsForRSS());
  };

  /*
  useEffect(() => {
    const response1 = axios
      .get(API_URL_RSS + `allOptionsForActivatedYears/` + currentResponsableServiceStage.id)
      .then((res) => {
        let result = res.data;
        console.log('--------------> HI-HELLO: ', res.data);
        setAllOpts(result);
      })
  }, [])
*/

  const formik = useFormik({
    initialValues: {
      yearLabel: "", allYears: ["2022", "2021"]
    }, validationSchema: validationSchema, onSubmit: async (values) => {

      // console.log('------------------> yearLabel: ' + values.yearLabel);

      setConventionsForRSS([]);

      setShowLoader(true);
      // console.log('-----------------1-> 0908 id RSS: ' + currentResponsableServiceStage.id);

      const [res, err] = await queryApi("respServStg/allDemandesAnnulationsConventionsListByOptionForRSS?idRSS=" + currentResponsableServiceStage.id + "&yearLabel=" + values.yearLabel, {}, "PUT", false);
      console.log("********* sars0508", res);
      setConventionsForRSS([]);
      setConventionsForRSS(res);
      //setConventionsForRSS(res.map(result => [result.idEt, result.nomEt, result.departEt, result.paysConvention, result.dateConvention, result.dateDebut, result.dateFin, '']));

      if (err) {
        setShowLoader(false);
        // console.log('-----------------1-> 0306')
        setError({
          visible: true, message: JSON.stringify(err.errors, null, 2),
        });
      } else {
        // console.log('-----------------2-> 0508')

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

  return (<>
    <TheSidebar dataDAC={nbDemandesAnnulationConvention} dataDC={nbDepositedConventions}
                dataVC={nbValidatedConventions}/>

    <CRow>
      <CCol>
        <CCard data-tut="reactour__1">
          <CRow>
            <CCol md="3"/>
            <CCol md="6">
              <CForm onSubmit={formik.handleSubmit}>
                <CFormGroup>
                  {error.visible && <p>{error.message}</p>}
                </CFormGroup>
                <center>
                  Choisir une Promotion pour visualiser la liste correspondante :
                </center>
                <br/>
                <CFormGroup row>
                  <CCol md="1"/>
                  <CCol md="10">
                    <CSelect value={formik.values.yearLabel}
                             onChange={formik.handleChange}
                      //onSelect={gotAllOptionsByPromotion(formik.values.yearLabel)}
                             onBlur={formik.handleBlur}
                             custom
                             size="sm"
                             name="yearLabel">
                      <option style={{display: "none"}}>
                        ---- Choisir une Promotion ----
                      </option>
                      {formik.values.allYears?.map((e, i) => (<option value={e} key={i}>
                        {e}
                      </option>))}
                    </CSelect>
                    {formik.errors.yearLabel && formik.touched.yearLabel &&
                      <p style={{color: "red"}}>{formik.errors.yearLabel}</p>}
                    <br/>
                  </CCol>
                  <CCol md="1"/>
                </CFormGroup>
                <center>
                  <CButton color="danger" type="submit">
                    {showLoader && <CSpinner grow size="sm"/>} &nbsp; Confirmer
                  </CButton>
                </center>
              </CForm>
            </CCol>
            <CCol md="3"/>
          </CRow>

          <br/>

          <CCardBody>
            {conventionsForRSS ? (<MUIDataTable
              data={conventionsForRSS}
              columns={columnsConventions}
              options={{
                selectableRows: 'none' // <===== will turn off checkboxes in rows
              }}
            />) : (<>
              Sorry, no Data is available
            </>)}
          </CCardBody>
        </CCard>
      </CCol>
    </CRow>

  </>);
};

export default ConventionsDemandesAnnulation;
