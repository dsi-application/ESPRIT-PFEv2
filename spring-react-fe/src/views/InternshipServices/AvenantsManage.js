import CIcon from "@coreui/icons-react";
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
import React, {useState, useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import {Link} from "react-router-dom";
import MUIDataTable from "mui-datatables";
import "moment/locale/fr";
import {getEtudiant} from "../../redux/slices/FichePFESlice";
import {createMuiTheme} from "@material-ui/core";
import {useFormik} from "formik";
import AuthService from "../services/auth.service";
import {queryApi} from "../../utils/queryApi";
import * as Yup from "yup";
import {selectAvenant} from "../../redux/slices/AvenantSlice";

const validationSchema = Yup.object().shape({
  yearLabel: Yup.string().required("* Année est un Champ obligatoire !."),
});

const currentResponsableServiceStage = AuthService.getCurrentResponsableServiceStage();
const API_URL_RSS = process.env.REACT_APP_API_URL_RSS;

const AvenantsManage = () => {
  const [isTourOpen, setIsTourOpen] = useState(false);
  const [avenantsForRSS, setAvenantsForRSS] = useState([]);

  const [responsive, setResponsive] = useState("vertical");
  const [tableBodyHeight, setTableBodyHeight] = useState("300");
  const [tableBodyMaxHeight, setTableBodyMaxHeight] = useState("");
  const [studentId, setStudentId] = useState(sessionStorage.getItem("studentId"));
  const [showLoader, setShowLoader] = useState(false);
  const [error, setError] = useState({visible: false, message: ""});
  const [danger, setDanger] = useState(false);
  const [allOpts, setAllOpts] = useState([]);

  const avenantsstatus = useSelector(
    (state) => state.persistedReducer.avenants.avenantsstatus
  );

  const dispatch = useDispatch();
  const columnsAvenants = [
    {
      name: "idEt",
      label: "Identifiant Étudiant",
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
      name: "currentClasse",
      label: "Classe",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "departEt",
      label: "Département",
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
      name: "traiter",
      label: "ÉTAT",
      options: {
        filter: true,
        sort: true,
        customBodyRender: (e) => {
          return (
            <td data-tut="reactour__2">

              {e ? (
                <CBadge color="success">TRAITE</CBadge>
              ) : (
                <CBadge color="warning">EN ATTENTE</CBadge>
              )}
            </td>
          );
        },
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

              <Link to={"/AvenantDetails"}>
                <CButton
                  variant="outline"
                  color="dark"
                  size="sm"
                  onClick={() => onClickAvenant(avenantsForRSS[dataIndex])}
                  data-tut="reactour__3"
                >

                  <CTooltip content=" Afficher détails">
                    <CIcon name="cil-magnifying-glass"></CIcon>
                  </CTooltip>
                </CButton>
              </Link>
              &nbsp; &nbsp;


              {avenantsForRSS[dataIndex].traiter ? (
                <CButton
                  variant="outline"
                  color="danger" Y
                  size="sm"
                  onClick={() => {
                    Download(avenantsForRSS[dataIndex].pathAvenant);
                  }}
                >
                  <CTooltip content="Télécharger Avenant">
                    <CIcon name="cil-save"></CIcon>
                  </CTooltip>
                  &nbsp;
                </CButton>
              ) : (
                <CButton
                  variant="outline"
                  color="danger"
                  size="sm"
                  disabled
                >
                  <CTooltip content="Télécharger Avenant">
                    <CIcon name="cil-save"></CIcon>
                  </CTooltip>
                </CButton>
              )}

              &nbsp; &nbsp;


              {avenantsForRSS[dataIndex].pathSignedAvenant !== null ? (
                <CButton
                  variant="outline"
                  color="success"
                  size="sm"
                  onClick={() => {
                    DownloadSignedAvenant(avenantsForRSS[dataIndex].pathSignedAvenant);
                  }}
                >
                  <CTooltip content="Télécharger Avenant Signé">
                    <CIcon name="cil-save"></CIcon>
                  </CTooltip>
                  &nbsp;
                </CButton>
              ) : (
                <>
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </>
              )}


            </>
          );
        },
      },
    }
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

  const onClickAvenant = (i) => {

    console.log('-------- onClickAven1: ' + i);
    dispatch(getEtudiant(i.idEt));
    dispatch(selectAvenant(i));
    document.body.style.overflowY = "auto"
  }

  const btnDownloadDisplay = (c) => {
    if (c.traiter === "02" || c.traiter === "03") {
      return (
        <CButton variant="outline"
                 color="danger"
                 size="sm"
                 onClick={() => {
                   Download(c);
                 }}>
          <CTooltip content="Télécharger Avenant">
            <CIcon name="cil-save"></CIcon>
          </CTooltip>
        </CButton>
      );
    } else {
      return (
        <CButton variant="outline" color="danger" size="sm" disabled>
          <CTooltip content="Télécharger Avenant">
            <CIcon name="cil-save"></CIcon>
          </CTooltip>
        </CButton>
      );
    }
  };

  useEffect(() => {
    const response1 = axios
      .get(API_URL_RSS + `allOptionsForActivatedYears/` + currentResponsableServiceStage.id)
      .then((res) => {
        let result = res.data;
        console.log('--------------> HI-HELLO: ', res.data);
        setAllOpts(result);
      })
  }, [])

  const formik = useFormik({
    initialValues: {
      yearLabel: "",
      allYears: ["2022", "2021"]
    },
    validationSchema: validationSchema,
    onSubmit: async (values) => {

      // console.log('------------------> yearLabel: ' + values.yearLabel);

      setAvenantsForRSS([]);

      setShowLoader(true);
      // console.log('-----------------1-> 0908 id RSS: ' + currentResponsableServiceStage.id);

      const [res, err] = await queryApi(
        "respServStg/allAvenantsList?idRSS=" + currentResponsableServiceStage.id +
        "&yearLabel=" + values.yearLabel,
        {},
        "PUT",
        false
      );
      console.log("********* sars0508", res);
      setAvenantsForRSS([]);
      setAvenantsForRSS(res);

      if (err) {
        setShowLoader(false);
        // console.log('-----------------1-> 0306')
        setError({
          visible: true,
          message: JSON.stringify(err.errors, null, 2),
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

  const Download = (p) => {
    console.log('--------------------> 19.09.22' + p);
    axios
      .get(
        `${process.env.REACT_APP_API_URL}` +
        "encadrement/download?fileName=" +
        encodeURIComponent(encodeURIComponent(p)),

        {responseType: "blob"}
      )
      .then((response) => {
        const file = new Blob([response.data], {type: "application/pdf"});
        let url = window.URL.createObjectURL(file);

        let a = document.createElement("a");
        a.href = url;
        window.open(url, '_blank').focus();

      });
  };

  const DownloadSignedAvenant = (p) => {
    console.log('--------------------> 19.09.22' + p);
    let encodedURL = encodeURIComponent(encodeURIComponent(p));
    axios.get(
      `${process.env.REACT_APP_API_URL}` +
      "encadrement/download?fileName=" +
      encodeURIComponent(encodeURIComponent(p)),

      {responseType: "blob"}
    )
      .then((response) => {
        const file = new Blob([response.data], {type: "application/pdf"});
        let url = window.URL.createObjectURL(file);

        let a = document.createElement("a");
        a.href = url;
        window.open(url, '_blank').focus();
      });
  };

  return (
    <>

      {avenantsstatus === "loading" || avenantsstatus === "noData" ? (
        <div>
          <CRow>
            <CCol md="12">
              <center>
                <br/><br/>
                <span className="waitIcon"/>
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
                            {formik.values.allYears?.map((e, i) => (
                              <option value={e} key={i}>
                                {e}
                              </option>
                            ))}
                          </CSelect>
                          {
                            formik.errors.yearLabel && formik.touched.yearLabel &&
                            <p style={{color: "red"}}>{formik.errors.yearLabel}</p>
                          }
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
                  {avenantsForRSS ? (
                    <MUIDataTable
                      data={avenantsForRSS}
                      columns={columnsAvenants}
                      options={{
                        selectableRows: 'none' // <===== will turn off checkboxes in rows
                      }}
                    />
                  ) : (
                    <>
                      Sorry, no Data is available
                    </>
                  )}
                </CCardBody>
              </CCard>
            </CCol>
          </CRow>
          {avenantsForRSS ? (
            ""
          ) : (
            <></>
          )}
        </>
      )}
    </>
  );
};

export default AvenantsManage;
