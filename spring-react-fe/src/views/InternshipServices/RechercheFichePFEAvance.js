import CIcon from "@coreui/icons-react";
import {
  CButton,
  CCard,
  CCardBody,
  CCardHeader,
  CCol,
  CForm,
  CFormGroup,
  CLabel,
  CListGroup,
  CListGroupItem,
  CModal,
  CModalBody,
  CModalFooter,
  CModalHeader,
  CModalTitle,
  CRow,
  CSelect,
  CSpinner,
} from "@coreui/react";
import Select from "react-select";
import moment from "moment";
import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import MUIDataTable from "mui-datatables";
import "moment/locale/fr";
import {
  fetchListRechercheFiche,
  selectAllAnnee,
  selectAllClasse,
  selectAllFilére,
  selectAlltechno,
  selectListRechercheFiche,
} from "../../redux/slices/ListingSlice";
import { useFormik } from "formik";
import makeAnimated from "react-select/animated";
import { createMuiTheme } from "@material-ui/core";
import { MuiThemeProvider } from "@material-ui/core";

import { getFiche, selectfichePFE } from "../../redux/slices/RequestSlice";
import { ExportCSV } from "../../utils/ExcportCSV";
import { etat } from "../Monotoring/StudentSupervision";
const RechercheFichePFEAvance = () => {
  const animatedComponents = makeAnimated();
  const [error, setError] = useState({ visible: false, message: "" });
  const dispatch = useDispatch();
  const [responsive, setResponsive] = useState("vertical");
  const [tableBodyHeight, setTableBodyHeight] = useState("300");
  const [tableBodyMaxHeight, setTableBodyMaxHeight] = useState("");
  const [spinner, setSpinner] = useState(false);
  const [Fiches, err] = useSelector(selectListRechercheFiche);
  const fichePFE = useSelector(selectfichePFE);
  const [info, setInfo] = useState(false);
  const [AllClasse, err1] = useSelector(selectAllClasse);
  const [Alltechno, err2] = useSelector(selectAlltechno);
  const [AllFilére, err3] = useSelector(selectAllFilére);
  const [AllAnnee, err4] = useSelector(selectAllAnnee);
  const [show, setShow] = useState(true);
  let selectRef = null;
  const theme = createMuiTheme({
    overrides: {
      MuiTableCell: {
        root: {
          padding: "2px 2px 2px 20px",
        },
      },
    },
  });
  const ListRechercheFicheStatus = useSelector(
    (state) => state.persistedReducer.listing.ListRechercheFicheStatus
  );

  let listtoExport = [];
  let allTechnologies = [];
 /* if (Fiches.length !== 0) {
    Fiches.map((s) => {
      var obj = {
        "Identifiant Etudiant": s.idEt,
        "Nom Etudiant": s.prenomet,
        "Prénom Etudiant": s.nomet,
        "Numéro Téléphone Etudiant": s.telet,
        "Adresse éléctronique": s.adressemailesp,
        "Classe Etudiant": s.classe,
        "Année universitaire Associée": s.anneeDebInscription,
        "Encadrant Pédagogique": s.encadrant,
      };
      listtoExport.push(obj);
    });
  }*/
  if (Alltechno.length !== 0) {
    Alltechno.map((s) => {
      var obj = {
        value: s.name,
        label: s.name,
        color: "#00B8D9",
      };
      allTechnologies.push(obj);
    });
  }

  const options = {
    //count: 1000000000000000000,
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
  const onClickFiche = (f) => {
    dispatch(getFiche(f.idEt, f.dateFiche));
    setInfo(!info);
  };
  const columns = [
    {
      name: "idEt",
      label: "Identifiant ",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "nomet",
      label: "Nom ",
      options: {
        filter: true,
        sort: true,
        options: {
          filter: true,
          sort: true,
        },
      },
    },
    {
      name: "prenomet",
      label: "Prénom ",
      options: {
        filter: true,
        sort: true,
        options: {
          filter: true,
          sort: true,
        },
      },
    },
    {
      name: "adressemailesp",
      label: "Adresse éléctronique",
      options: {
        filter: true,
        sort: true,
        options: {
          filter: true,
          sort: true,
        },
      },
    },
    {
      name: "telet",
      label: "Téléphone",
      options: {
        filter: true,
        sort: true,
        options: {
          filter: true,
          sort: true,
        },
      },
    },
    {
      name: "anneeDebInscription",
      label: "Année universitaire associée",
      options: {
        filter: true,
        sort: true,
        options: {
          filter: true,
          sort: true,
        },
      },
    },
    {
      name: "classe",
      label: "Classe",
      options: {
        filter: true,
        sort: true,
        options: {
          filter: true,
          sort: true,
        },
      },
    },
    {
      name: "encadrant",
      label: "Encadrant Pédagogique",
      options: {
        filter: true,
        sort: true,
        options: {
          filter: true,
          sort: true,
        },
      },
    },
    {
      name: "",
      label: "Détails Fiche",
      options: {
        filter: true,
        sort: true,
        customBodyRenderLite: (dataIndex) => {
          return (
            <CButton
              variant="outline"
              color="dark"
              size="sm"
              onClick={() => onClickFiche(Fiches[dataIndex])}
            >
               <CIcon name="cil-magnifying-glass" />
            </CButton>
          );
        },
      },
    },
  ];

  const formik = useFormik({
    initialValues: {
      annee: "",
      classe: "",
      etat: "",
      Techno: [],
      codeFilere: "",
    },
    onSubmit: async (values) => {
      setSpinner(true);
      if (formik.values.Techno === null) {
        dispatch(
          fetchListRechercheFiche(
            values.annee,
            values.classe,
            values.etat,
            values.codeFilere,
            []
          )
        );
        setSpinner(false);
        //  setShow(true);
      } else {
        let allTechnologieslist = [];
        if (values.Techno.length !== 0) {
          values.Techno.map((s) => {
            allTechnologieslist.push(s.value);
          });
        }
        dispatch(
          fetchListRechercheFiche(
            values.annee,
            values.classe,
            values.etat,
            values.codeFilere,
            allTechnologieslist
          )
        );
        setSpinner(false);
        // setShow(true);
      }
    },
  });

  const clearValue = () => {
    formik.resetForm();
    selectRef.select.clearValue();
  };
  return (
    <>
      <CRow>
        <CCol>
          <CCard>
            <CCardBody>
              <CForm onSubmit={formik.handleSubmit}>
                <CFormGroup>
                  
                  {error.visible && <p>{error.message}</p>}
                </CFormGroup>
                <CFormGroup row>
                  <CCol md="3">
                    <CLabel htmlFor="selectSm">
                      <b>Année universitaire : </b>
                    </CLabel>
                  </CCol>
                  <CCol xs="12" md="9">
                    <CSelect
                      value={formik.values.annee}
                      onChange={formik.handleChange}
                      onBlur={formik.handleBlur}
                      custom
                      name="annee"
                    >
                      <option style={{ display: "none" }}>
                        
                        ---- Selectionner une année universitaire ----
                      </option>
                      {AllAnnee?.map((e, i) => (
                        <option value={e[0]} key={i}>
                          {e[0]} - {e[1]}
                        </option>
                      ))}
                    </CSelect>
                    {formik.errors.annee && formik.touched.annee && (
                      <p style={{ color: "red" }}>{formik.errors.annee}</p>
                    )}
                  </CCol>
                </CFormGroup>
                <CFormGroup row>
                  <CCol md="3">
                    <CLabel htmlFor="selectSm">
                      <b>Classe : </b>
                    </CLabel>
                  </CCol>
                  <CCol xs="12" md="9">
                    <CSelect
                      value={formik.values.classe}
                      onChange={formik.handleChange}
                      onBlur={formik.handleBlur}
                      custom
                      name="classe"
                    >
                      <option style={{ display: "none" }}>
                        
                        ---- Selectionner une classe ----
                      </option>
                      {AllClasse?.map((e, i) => (
                        <option value={e} key={i}>
                          {e}
                        </option>
                      ))}
                    </CSelect>
                    {formik.errors.classe && formik.touched.classe && (
                      <p style={{ color: "red" }}>{formik.errors.classe}</p>
                    )}
                  </CCol>
                </CFormGroup>
                <CFormGroup row>
                  <CCol md="3">
                    <CLabel htmlFor="selectSm">
                      <b>Etat Plan Travail : </b>
                    </CLabel>
                  </CCol>
                  <CCol xs="12" md="9">
                    <CSelect
                      value={formik.values.etat}
                      onChange={formik.handleChange}
                      onBlur={formik.handleBlur}
                      custom
                      name="etat"
                    >
                      <option style={{ display: "none" }}>
                        
                        ---- Selectionner un Etat Plan Travail ----
                      </option>
                      <option value="03">VALIDEE</option>
                      <option value="04">REFUSEE</option>
                      <option value="05">ANNULEE</option>
                      <option value="06">A_SOUTENIR</option>
                      <option value="07">SOUTENUE</option>
                    </CSelect>
                    {formik.errors.etat && formik.touched.etat && (
                      <p style={{ color: "red" }}>{formik.errors.etat}</p>
                    )}
                  </CCol>
                </CFormGroup>
                <CFormGroup row>
                  <CCol md="3">
                    <CLabel htmlFor="selectSm">
                      <b>Téchnologies utilisées : </b>
                    </CLabel>
                  </CCol>
                  <CCol xs="12" md="9">
                    <Select
                      ref={(ref) => {
                        selectRef = ref;
                      }}
                      closeMenuOnSelect={true}
                      components={animatedComponents}
                      isMulti
                      isClearable={true}
                      value={allTechnologies.value}
                      options={allTechnologies}
                      placeholder="Choisir une / plusieurs téchnologies"
                      onChange={(evt) => {
                        formik.setFieldValue("Techno", evt);
                      }}
                    />

                    {formik.errors.Techno && formik.touched.Techno && (
                      <p style={{ color: "red" }}>{formik.errors.Techno}</p>
                    )}
                  </CCol>
                </CFormGroup>
                <CFormGroup row>
                  <CCol md="3">
                    <CLabel htmlFor="selectSm">
                      <b>Spécialité : </b>
                    </CLabel>
                  </CCol>
                  <CCol xs="12" md="9">
                    <CSelect
                      value={formik.values.codeFilere}
                      onChange={formik.handleChange}
                      onBlur={formik.handleBlur}
                      custom
                      name="codeFilere"
                    >
                      <option style={{ display: "none" }}>
                        
                        ---- Selectionner une Spécialité ----
                      </option>
                      <option value="">
                        
                        ---- Selectionner une Spécialité ----
                      </option>
                      {AllFilére?.map((e, i) => (
                        <option value={e[1]} key={i}>
                          {e[0]}
                        </option>
                      ))}
                    </CSelect>
                    {formik.errors.codeFilere && formik.touched.codeFilere && (
                      <p style={{ color: "red" }}>{formik.errors.codeFilere}</p>
                    )}
                  </CCol>
                </CFormGroup>
                <CRow>
                  <CCol xs="12" md="11">
                    <CButton
                      className="float-right"
                      name="btnsubmit"
                      type="submit"
                      color="danger"
                      size="lg"
                    >
                      <CIcon name="cil-magnifying-glass" />

                      {ListRechercheFicheStatus === "loading" && (
                        <CSpinner color="dark" size="sm" />
                      )}
                    </CButton>
                  </CCol>

                  <CCol xs="12" md="1">
                    <CButton
                      size="lg"
                      className="float-right"
                      name="btnsubmit"
                      color="secondary"
                      //   type="reset"
                      onClick={clearValue}
                    >
                        <CIcon name="cil-x" />
                    </CButton>
                  </CCol>
                </CRow>
              </CForm>
              <br></br> <br></br>
              <br></br>
              {ListRechercheFicheStatus === "loading" ? (
                <>
                  <div style={{ textAlign: "center" }}>
                    <CSpinner color="danger" grow size="lg" />
                    &nbsp;
                  </div>
                  <br></br>
                </>
              ) : (
                <>
                  {Fiches ? (
                    <>
                      
                      <ExportCSV
                        csvData={listtoExport}
                        fileName="StudentList"
                      />
                       <MuiThemeProvider theme={theme}>
                      <MUIDataTable
                        title={"Résultat Recherche"}
                        data={Fiches}
                        columns={columns}
                        options={options}
                      /></MuiThemeProvider>
                    </>
                  ) : (
                    <MuiThemeProvider theme={theme}>
                    <MUIDataTable
                      title={"Résultat Recherche"}
                      columns={columns}
                      options={options}
                    /></MuiThemeProvider>
                  )}
                </>
              )}
            </CCardBody>
          </CCard>
        </CCol>
      </CRow>
      <CModal size="lg" show={info} onClose={() => setInfo(!info)} color="dark">
        <CModalHeader closeButton>
          <CModalTitle>Détails du Plan Travail</CModalTitle>
        </CModalHeader>
        <CModalBody>
          <CCol xs="12" sm="12" md="12">
            {fichePFE.idFichePFE !== undefined && (
              <CCard accentColor="danger">
                <CCardHeader>
                  <CRow>
                    <CCol xs="12" sm="6" md="6">
                      <span className="redMark">
                        {fichePFE.titreProjet}
                      </span>
                      <br></br>

                      <CRow>
                        <CCol md="3">
                          <span className="greyFieldLibelleCardSmall">Identifiant Étudiant :</span>
                        </CCol>
                        <CCol md="9">
                          {fichePFE.idFichePFE.idEt}
                        </CCol>
                      </CRow>

                      <CRow>
                        <CCol md="3">
                          <span className="greyFieldLibelleCardSmall">Date Dépôt Fiche :</span>
                        </CCol>
                        <CCol md="9">
                          {moment(fichePFE.idFichePFE.dateDepotFiche).locale("fr").format("LLLL")}
                        </CCol>
                      </CRow>
                    </CCol>
                  </CRow>
                </CCardHeader>
                <CCardBody>
                  <CRow>
                    <CCol xs="12" sm="6" md="6">
                      {etat(fichePFE.etatFiche)}
                      <br></br>
                      <span className="primaryMark">Description Projet :</span>
                      <p>
                        {fichePFE.descriptionProjet
                            ? fichePFE.descriptionProjet
                            : "Pas de decsription"}
                      </p>
                      <br/>
                      <span className="infoMark">Problématiques Projet :</span>
                      <CListGroup accent>
                        {fichePFE.problematics?.map((item, i) => (
                          <CListGroupItem accent="danger">
                            {item.name}
                          </CListGroupItem>
                        ))}
                      </CListGroup>
                    </CCol>
                    <CCol xs="12" sm="6" md="6">
                      <span className="secondaryMark">Détails Fonctionnalités :</span>
                      <CListGroup accent>
                        {fichePFE.functionnalities?.map((item, i) => (
                            <CListGroupItem accent="dark">
                              {item.name} <br></br> <b>Description : </b>
                              {item.description}
                            </CListGroupItem>
                        ))}
                      </CListGroup>
                      <br/>
                      <span className="lightMark">Téchnologies Utilisées : </span>
                      <CListGroup accent>
                        {fichePFE.technologies?.map((item, i) => (
                          <CListGroupItem accent="secondary">
                            {item.name} <br></br>
                          </CListGroupItem>
                        ))}
                      </CListGroup>
                    </CCol>
                  </CRow>
                </CCardBody>
              </CCard>
            )}
          </CCol>
        </CModalBody>
        <CModalFooter>
          <CButton color="secondary" onClick={() => setInfo(!info)}>
            Annuler
          </CButton>
        </CModalFooter>
      </CModal>
    </>
  );
};

export default RechercheFichePFEAvance;
