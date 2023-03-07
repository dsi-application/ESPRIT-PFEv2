import moment from "moment";
import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import Text from "antd/lib/typography/Text";
import "moment/locale/fr";
import MUIDataTable from "mui-datatables";
import "../../css/style.css";
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
  CModal,
  CModalBody,
  CModalFooter,
  CModalHeader,
  CModalTitle,
  CRow,
  CWidgetProgressIcon,
} from "@coreui/react";
import {
  addSession,
  deleteSession,
  selectSelectedSession,
  selectSession,
  selectSessions,
  setErrors,
  updateSession,
} from "../../../redux/slices/SessionSlice";
import * as Yup from "yup";
import { useFormik } from "formik";
import CIcon from "@coreui/icons-react";
import { queryApi } from "../../../utils/queryApi";
import Tour from "reactour";
import LocalLibrary from "@material-ui/icons/LocalLibrary";
import IconButton from "@material-ui/core/IconButton";
import Tooltip from "@material-ui/core/Tooltip";

import { createMuiTheme } from "@material-ui/core";
import { MuiThemeProvider } from "@material-ui/core";
const steps = [
  {
    selector: '[data-tut="reactour__1"]',
    content: `Içi, Vous allez trouver la liste des sessions`,
  },
  {
    selector: '[data-tut="reactour__2"]',
    content: `Consulter le total de tous les sessions disponibles`,
  },
  {
    selector: '[data-tut="reactour__3"]',

    content: () => (
      <Text>
        Si vous voulez ajouter une nouvelle session , vous pouvez cliquer sur ce
        bouton : : <strong>Ajouter une session</strong>.
      </Text>
    ),
  },
];
const validationSchema = Yup.object().shape({
  libelleSession: Yup.string()
    .min(5, "Minimum 5 caractéres !")
    .max(100, "Ne pas dépasser 100 caractéres !")
    .required("* Champ obligatoire !"),
  dateDebut: Yup.string().required("* Champ obligatoire !"),
  dateFin: Yup.string().required("* Champ obligatoire !"),
});
const SessionManagement = () => {
  const [showError2, setshowError2] = useState(false);

  const [showError3, setshowError3] = useState(false);
  const [isTourOpen, setIsTourOpen] = useState(false);
  const [danger, setDanger] = useState(false);
  const [info, setInfo] = useState(false);
  const dispatch = useDispatch();
  const [responsive, setResponsive] = useState("vertical");
  const [tableBodyHeight, setTableBodyHeight] = useState("300");
  const [tableBodyMaxHeight, setTableBodyMaxHeight] = useState("");
  const [sessions, err] = useSelector(selectSessions);
  const [showForm, setshowForm] = useState(false);
  const SelectedSession = useSelector(selectSelectedSession);
  const [error, setError] = useState({ visible: false, message: "" });

  const toggleFormRDV = () => {
    setshowForm(showForm ? false : true);
  };
  const toggleModifModal = (s) => {
    dispatch(selectSession(s));

    setInfo(!info);
  };
  const toggleDeleteModal = (s) => {
    dispatch(selectSession(s));
    setDanger(!danger);
  };
  const deleteSessionevent = async () => {
    const [err] = await queryApi(
      "config/sessions/" + SelectedSession.idSession,
      {},
      "DELETE"
    );
    if (err) {
      dispatch(setErrors(err));
    } else {
      dispatch(deleteSession(SelectedSession));
      setDanger(!danger);
    }
  };
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
  const theme = createMuiTheme({
    overrides: {
      MuiTableCell: {
        root: {
          padding: "1px 1px 1px 10px",
        },
      },
    },
  });
  const columns = [
    {
      name: "libelleSession",
      label: "Libellé",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "dateDebut",
      label: "Date Début",
      options: {
        filter: true,
        sort: true,
        customBodyRender: (e) => {
          return <td> {moment(e).format("LL")} </td>;
        },
      },
    },
    {
      name: "dateFin",
      label: "Date Fin",
      options: {
        filter: true,
        sort: true,
        customBodyRender: (e) => {
          return <td> {moment(e).format("LL")} </td>;
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

                  <CButton
                    variant="outline"
                    color="dark"
                    size="sm"
                    onClick={() => toggleModifModal(sessions[dataIndex])}
                  >
                     <CIcon name="cil-pencil"></CIcon>
                  </CButton>

                  &nbsp;
                  {sessions[dataIndex].nbr === 0 ? (
                    <CButton
                      variant="outline"
                      color="danger"
                      size="sm"
                      onClick={() => toggleDeleteModal(sessions[dataIndex])}
                    >
                       <CIcon name="cil-trash"></CIcon>
                    </CButton>
                  ) : (
                    <CButton
                      variant="outline"
                      color="danger"
                      size="sm"
                      disabled
                    >
                       <CIcon name="cil-trash"></CIcon>
                    </CButton>
                  )}

            </>
          );
        },
      },
    },
  ];
  const formik = useFormik({
    initialValues: {
      libelleSession: "",
      dateDebut: "",
      dateFin: "",
      fichePfes: [],
    },
    validationSchema: validationSchema,
    onSubmit: async (values) => {
      const [res, err] = await queryApi(
        "config/sessions",
        values,
        "POST",
        false
      );
      if (err) {
        setError({
          visible: true,
          message: JSON.stringify(err.errors, null, 2),
        });
      } else {
        if ( values.dateFin > values.dateDebut) {
          dispatch(addSession(res));
          formik.resetForm();
          setshowError2(false);
          setshowForm(!showForm);

        } else {
          setshowError2(true)
        }

      }
    },
  });
  const formikModif = useFormik({
    enableReinitialize: true,
    initialValues: {
      idSession: SelectedSession.idSession,
      libelleSession: SelectedSession.libelleSession,
      dateDebut: SelectedSession.dateDebut,
      dateFin: SelectedSession.dateFin,
    },
    validationSchema: validationSchema,
    onSubmit: async (values) => {
      const [res, err] = await queryApi(
        "config/sessions/" + SelectedSession.idSession,
        values,
        "PUT",
        false
      );
      if (err) {
        setError({
          visible: true,
          message: JSON.stringify(err.errors, null, 2),
        });
      } else {
        if ( values.dateFin > values.dateDebut) {
          dispatch(updateSession(res));
          setshowError3(false);
          setInfo(!info);
        }
        else {
          setshowError3(true);
        }
      }
    },
  });

  return (
    <>
      <Tour
        steps={steps}
        isOpen={isTourOpen}
        onAfterOpen={(target) => (document.body.style.overflowY = "hidden")}
        onBeforeClose={(target) => (document.body.style.overflowY = "auto")}
        onRequestClose={() => setIsTourOpen(false)}
      />
      <CRow>
        
        <CCol sm="2" md="7">
          <CCard>
            <CRow>
              <CCol xs="12">
                <br/>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <span style={{color: "#b30000", fontSize: "14px", fontWeight: "bold"}}>Liste des Sessions</span>
              </CCol>
            </CRow>
            <br/>
            <CCardBody data-tut="reactour__1">
              {sessions ? (
                 <MuiThemeProvider theme={theme}>
                <MUIDataTable
                  title={""}
                  data={sessions}
                  columns={columns}
                  options={options}
                /></MuiThemeProvider>
              ) : (
                <MuiThemeProvider theme={theme}>
                <MUIDataTable
                  title={""}
                  //   data={sessions}
                  columns={columns}
                  options={options}
                /></MuiThemeProvider>
              )}
            </CCardBody>
          </CCard>
        </CCol>
        <CCol sm="2" md="5">
          <CCard>
            <CCardHeader>
              
              <CWidgetProgressIcon
                data-tut="reactour__2"
                header={sessions.length}
                text="Nombre Total SESSIONS "
                color="gradient-dark"
                inverse
              >
                <CIcon name="cil-speedometer" height="36" />
              </CWidgetProgressIcon>
              <CButton
                data-tut="reactour__3"
                className="float-right"
                variant="outline"
                color="danger"
                size="sm"
                onClick={() => toggleFormRDV()}
              >
                Ajouter une session <CIcon name="cil-puzzle"></CIcon>
              </CButton>
            </CCardHeader>

            <CCardBody>
              {showForm ? (
                <CForm onSubmit={formik.handleSubmit}>
                  <CFormGroup>
                    
                    {error.visible && <p>{error.message}</p>}
                  </CFormGroup>
                  <CFormGroup row>
                    <CCol md="3">
                      <CLabel>
                        <b>Libellé Session :</b>
                      </CLabel>
                    </CCol>
                    <CCol xs="12" md="9">
                      <CInput
                        value={formik.values.libelleSession}
                        onChange={formik.handleChange}
                        type="input"
                        name="libelleSession"
                        placeholder="Saisir Nom de la session"
                      />
                      {formik.errors.libelleSession &&
                        formik.touched.libelleSession && (
                          <p style={{ color: "red" }}>
                            {formik.errors.libelleSession}
                          </p>
                        )}

                      <br />
                    </CCol>
                  </CFormGroup>
                  <CFormGroup row>
                    <CCol md="3">
                      <CLabel htmlFor="date-input">
                        
                        <b>Date Début  :</b>
                      </CLabel>
                    </CCol>
                    <CCol xs="12" md="9">
                      <CInput
                        value={moment(formik.values.dateDebut)
                          .format("YYYY-MM-DD")
                          .toString()}
                        onChange={formik.handleChange}
                        type="date"
                        name="dateDebut"
                        placeholder="Date Début Session"
                      />
                      {formik.errors.dateDebut && formik.touched.dateDebut && (
                        <p style={{ color: "red" }}>
                          {formik.errors.dateDebut}
                        </p>
                      )}
                      {showError2 && (
                        <p style={{ color: "red" }}>
                          * La date du début doit être inférieure à la date de la
                          fin.
                        </p>
                      )}
                      <br />
                    </CCol>
                  </CFormGroup>
                  <CFormGroup row>
                    <CCol md="3">
                      <CLabel htmlFor="date-input">
                        
                        <b>Date Fin  :</b>
                      </CLabel>
                    </CCol>
                    <CCol xs="12" md="9">
                      <CInput
                        value={moment(formik.values.dateFin)
                          .format("YYYY-MM-DD")
                          .toString()}
                        onChange={formik.handleChange}
                        type="date"
                        name="dateFin"
                        placeholder="Date Fin Session"
                      />
                      {formik.errors.dateFin && formik.touched.dateFin && (
                        <p style={{ color: "red" }}>{formik.errors.dateFin}</p>
                      )}
                      {showError2 && (
                        <p style={{ color: "red" }}>
                          * La date du début doit être inférieure à la date de la
                          fin.
                        </p>
                      )}
                      <br />
                    </CCol>
                  </CFormGroup>
                  <CButton
                    className="float-right"
                    name="btnsubmit"
                    type="submit"
                    color="danger"
                  >
                    Enregister
                  </CButton>
                </CForm>
              ) : (
                <></>
              )}
              <br></br>
            </CCardBody>
          </CCard>
        </CCol>
      </CRow>

      <CModal size="lg" show={info} onClose={() => setInfo(!info)} color="dark">
        <CModalHeader closeButton>
          <CModalTitle><span style={{fontSize: "14px"}}>Modification Détails Session</span></CModalTitle>
        </CModalHeader>
        <CModalBody>
          <CForm onSubmit={formikModif.handleSubmit}>
            <CFormGroup> {error.visible && <p>{error.message}</p>}</CFormGroup>
            <CFormGroup row>
              <CCol md="3">
                <CLabel>
                  <b>Libellé Session :</b>
                </CLabel>
              </CCol>
              <CCol xs="12" md="9">
                <CInput
                  value={formikModif.values.libelleSession}
                  onChange={formikModif.handleChange}
                  type="input"
                  name="libelleSession"
                  placeholder="Saisir Nom de la session"
                />
                {formikModif.errors.libelleSession &&
                  formikModif.touched.libelleSession && (
                    <p style={{ color: "red" }}>
                      {formikModif.errors.libelleSession}
                    </p>
                  )}

                <br />
              </CCol>
            </CFormGroup>
            <CFormGroup row>
              <CCol md="3">
                <CLabel htmlFor="date-input">
                  
                  <b>Date Début Session :</b>
                </CLabel>
              </CCol>
              <CCol xs="12" md="9">
                <CInput
                  value={moment(formikModif.values.dateDebut)
                    .format("YYYY-MM-DD")
                    .toString()}
                  onChange={e => {
                    formikModif.setFieldValue('dateDebut', e.target.value)

                    formikModif.setFieldValue('dateFin', "");
                  }}
                  type="date"
                  name="dateDebut"
                  placeholder="Date Début Session"
                />
                {formikModif.errors.dateDebut &&
                  formikModif.touched.dateDebut && (
                    <p style={{ color: "red" }}>
                      {formikModif.errors.dateDebut}
                    </p>
                  )}
                {showError3 && (
                  <p style={{ color: "red" }}>
                    (*) Date Début doit être inférieure à Date Fin.
                  </p>
                )}
                <br />
              </CCol>
            </CFormGroup>
            <CFormGroup row>
              <CCol md="3">
                <CLabel htmlFor="date-input">
                  
                  <b>Date Fin Session :</b>
                </CLabel>
              </CCol>
              <CCol xs="12" md="9">
                <CInput
                  value={moment(formikModif.values.dateFin)
                    .format("YYYY-MM-DD")
                    .toString()}
                  onChange={formikModif.handleChange}
                  type="date"
                  name="dateFin"
                  placeholder="Date Fin Session"
                />
                {formikModif.errors.dateFin && formikModif.touched.dateFin && (
                  <p style={{ color: "red" }}>{formikModif.errors.dateFin}</p>
                )}
                {showError3 && (
                  <p style={{ color: "red" }}>
                    (*) Date Début doit être inférieure à Date Fin.
                  </p>
                )}
                <br />
              </CCol>
            </CFormGroup>
            <CRow>
              <CCol md="12">
                <div className="float-right">
                  <CButton
                    name="btnsubmit"
                    type="submit"
                    color="dark"
                  >
                    Enregister
                  </CButton>
                  &nbsp;&nbsp;
                  <CButton
                    color="secondary"
                    onClick={() => setInfo(!info)}
                  >
                    Non
                  </CButton>
                </div>
              </CCol>
            </CRow>
          </CForm>
        </CModalBody>
      </CModal>
      <CModal show={danger} onClose={() => setDanger(!danger)} color="danger">
        <CModalHeader closeButton>
          <CModalTitle><span style={{fontSize: "14px"}}>Confirmation
            </span></CModalTitle>
        </CModalHeader>
        <CModalBody>
          <center>
            <br/>
            Voulez-vous vraiment supprimer la session
            <br/>
            <span className="redMark">{SelectedSession.libelleSession}</span> ?.
            <br/><br/>
          </center>
        </CModalBody>
        <CModalFooter>
          <CButton color="danger" onClick={() => deleteSessionevent()}>
            Oui
          </CButton>
          <CButton color="secondary" onClick={() => setDanger(!danger)}>
            Non
          </CButton>
        </CModalFooter>
      </CModal>
    </>
  );
};

export default SessionManagement;
