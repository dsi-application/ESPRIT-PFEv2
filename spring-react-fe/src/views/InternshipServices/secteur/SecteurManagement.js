import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";

import { createMuiTheme } from "@material-ui/core";
import { MuiThemeProvider } from "@material-ui/core";
import "moment/locale/fr";
import "../../css/style.css";
import MUIDataTable from "mui-datatables";
import {
  CButton,
  CCard,
  CCardBody,
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
  addSecteur,
  deleteSecteur,
  selectSecteur,
  selectSecteurs,
  selectSelectedSecteur,
  setErrors,
  updateSecteur,
} from "../../../redux/slices/SecteurSlice";
import CIcon from "@coreui/icons-react";
import { useFormik } from "formik";
import { queryApi } from "../../../utils/queryApi";
import * as Yup from "yup";
import Tour from "reactour";
import LocalLibrary from "@material-ui/icons/LocalLibrary";
import IconButton from "@material-ui/core/IconButton";
import Tooltip from "@material-ui/core/Tooltip";
import Text from "antd/lib/typography/Text";
const steps = [
  {
    selector: '[data-tut="reactour__1"]',
    content: `Içi, Vous allez trouver la liste des secteurs des entreprises`,
  },
  {
    selector: '[data-tut="reactour__2"]',
    content: `Consulter le total de tous les secteurs disponibles`,
  },
  {
    selector: '[data-tut="reactour__3"]',

    content: () => (
      <Text>
        Si vous voulez ajouter un nouveau secteur , vous pouvez cliquer sur ce
        bouton : : <strong>Ajouter un secteur d'entreprise</strong>.
      </Text>
    ),
  },
];
const validationSchema = Yup.object().shape({
  libelleSecteur: Yup.string()
    .min(5, "Minimum 5 caractéres !")
    .max(100, "Ne pas dépasser 100 caractéres !")
    .required("* Champ obligatoire !"),
});
const SecteurManagement = () => {
  const [isTourOpen, setIsTourOpen] = useState(false);
  const [danger, setDanger] = useState(false);
  const [info, setInfo] = useState(false);
  const dispatch = useDispatch();
  const [responsive, setResponsive] = useState("vertical");
  const [tableBodyHeight, setTableBodyHeight] = useState("300");
  const [tableBodyMaxHeight, setTableBodyMaxHeight] = useState("");
  const [secteurs, err] = useSelector(selectSecteurs);
  const [showForm, setshowForm] = useState(false);
  const SelectedSecteur = useSelector(selectSelectedSecteur);
  const [error, setError] = useState({ visible: false, message: "" });
  const theme = createMuiTheme({
    overrides: {
      MuiTableCell: {
        root: {
          padding: "2px 2px 2px 20px",
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
  const deleteSecteurevent = async () => {
    const [err] = await queryApi(
      "config/secteurs/" + SelectedSecteur.idSecteur,
      {},
      "DELETE"
    );
    if (err) {
      dispatch(setErrors(err));
    } else {
      dispatch(deleteSecteur(SelectedSecteur));
      setDanger(!danger);
    }
  };
  const formik = useFormik({
    initialValues: {
      libelleSecteur: "",
    },
    validationSchema: validationSchema,
    onSubmit: async (values) => {
      const [res, err] = await queryApi(
        "config/secteurs",
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
        dispatch(addSecteur(res));
        // window.location.reload();
        formik.resetForm();
        setshowForm(!showForm);
      }
    },
  });
  const formikModif = useFormik({
    enableReinitialize: true,
    initialValues: {
      idSecteur: SelectedSecteur.idSecteur,
      libelleSecteur: SelectedSecteur.libelleSecteur,
      nbr: SelectedSecteur.nbr,
    },
    validationSchema: validationSchema,
    onSubmit: async (values) => {
      const [res, err] = await queryApi(
        "config/secteurs/" + SelectedSecteur.idSecteur,
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
        dispatch(updateSecteur(res));
        setInfo(!info);
      }
    },
  });
  const toggleModifModal = (s) => {
    dispatch(selectSecteur(s));

    setInfo(!info);
  };
  const toggleDeleteModal = (s) => {
    dispatch(selectSecteur(s));
    setDanger(!danger);
  };
  const columns = [
    {
      name: "libelleSecteur",
      label: "Libellé",
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

                  <CButton
                    variant="outline"
                    color="dark"
                    size="sm"
                    onClick={() => toggleModifModal(secteurs[dataIndex])}
                  >
                     <CIcon name="cil-pencil"></CIcon>
                  </CButton>
                  &nbsp;
                  {secteurs[dataIndex].nbr === 0 ? (
                    <CButton
                      variant="outline"
                      color="danger"
                      size="sm"
                      onClick={() => toggleDeleteModal(secteurs[dataIndex])}
                    >
                       <CIcon name="cil-trash"></CIcon>
                    </CButton>
                  ) : (
                    <></>
                  )}

            </>
          );
        },
      },
    },
  ];
  const toggleFormRDV = () => {
    setshowForm(showForm ? false : true);
    document.body.style.overflowY = "auto";
  };
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
        
        <CCol sm="2" md="6">
          <CCard data-tut="reactour__1">
            <CRow>
              <CCol xs="12">
                <br/>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <span style={{color: "#b30000", fontSize: "14px", fontWeight: "bold"}}>Liste des Secteurs Entreprise</span>
              </CCol>
            </CRow>
            <br/>
            <CCardBody>
              {secteurs ? (
                 <MuiThemeProvider theme={theme}>
                <MUIDataTable
                  title={""}
                  data={secteurs}
                  columns={columns}
                  options={options}
                /></MuiThemeProvider>
              ) : (
                <MuiThemeProvider theme={theme}>
                <MUIDataTable
                  title={""}
                  //   data={secteurs}
                  columns={columns}
                  options={options}
                /></MuiThemeProvider>
              )}
            </CCardBody>
          </CCard>
        </CCol>
        <CCol sm="2" md="6">
          <CCard>
            <CCardBody>
              <CWidgetProgressIcon
                data-tut="reactour__2"
                header={secteurs.length}
                text="Nombre Total SECTEUR D'entreprise"
                color="gradient-dark"
                inverse
              >
                <CIcon name="cil-speedometer" height="36" />
              </CWidgetProgressIcon>
              <CButton
                className="float-right"
                variant="outline"
                color="dark"
                size="sm"
                onClick={() => toggleFormRDV()}
                data-tut="reactour__3"
              >
                Ajouter un secteur d'entreprise
              </CButton>
              <br></br> <br></br>
              {showForm ? (
                <CForm onSubmit={formik.handleSubmit}>
                  <CFormGroup>
                    
                    {error.visible && <p>{error.message}</p>}
                  </CFormGroup>
                  <CFormGroup row>
                    <CCol md="3">
                      <CLabel>
                        <b>Nom / Libellé Secteur :</b>
                      </CLabel>
                    </CCol>
                    <CCol xs="12" md="9">
                      <CInput
                        value={formik.values.libelleSecteur}
                        onChange={formik.handleChange}
                        type="input"
                        name="libelleSecteur"
                        placeholder="Saisir Nom du secteur"
                      />
                      {formik.errors.libelleSecteur &&
                        formik.touched.libelleSecteur && (
                          <p style={{ color: "red" }}>
                            {formik.errors.libelleSecteur}
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
            </CCardBody>
          </CCard>
        </CCol>
      </CRow>

      <CModal size="lg" show={info} onClose={() => setInfo(!info)} color="dark">
        <CModalHeader closeButton>
          <CModalTitle><span style={{fontSize: "14px"}}>Modification Détails Secteur</span></CModalTitle>
        </CModalHeader>
        <CModalBody>
          <CForm onSubmit={formikModif.handleSubmit}>
            <CFormGroup> {error.visible && <p>{error.message}</p>}</CFormGroup>
            <CFormGroup row>
              <CCol md="3">
                <CLabel>
                  <b>Libellé Secteur :</b>
                </CLabel>
              </CCol>
              <CCol xs="12" md="9">
                <CInput
                  value={formikModif.values.libelleSecteur}
                  onChange={formikModif.handleChange}
                  type="input"
                  name="libelleSecteur"
                  placeholder="Saisir Nom de la session"
                />
                {formikModif.errors.libelleSecteur &&
                  formikModif.touched.libelleSecteur && (
                    <p style={{ color: "red" }}>
                      {formikModif.errors.libelleSecteur}
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
          <CModalTitle>
            <span style={{fontSize: "14px"}}>Confirmation
            </span></CModalTitle>
        </CModalHeader>
        <CModalBody>
          <center>
            <br/>
            Voulez-vous vraiment supprimer le secteur
            <br/>
            <span className="redMark">{SelectedSecteur.libelleSecteur}</span> ?.
            <br/><br/>
          </center>
        </CModalBody>
        <CModalFooter>
          <CButton color="danger" onClick={() => deleteSecteurevent()}>
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

export default SecteurManagement;
