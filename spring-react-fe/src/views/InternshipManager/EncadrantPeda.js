import {
  CAlert,
  CButton,
  CCard,
  CCardBody,
  CCardHeader,
  CCol,
  CModal,
  CModalBody,
  CModalFooter,
  CModalHeader,
  CModalTitle,
  CRow,
  CSpinner,
} from "@coreui/react";

import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import "moment/locale/fr";
import MUIDataTable from "mui-datatables";
import {
  fetchEtudiantsbyEns,
  selectEnseignants,
  selectEtudiantsbyEns,
} from "../../redux/slices/EncadrementSlice";
import CIcon from "@coreui/icons-react";

import TextField from "@material-ui/core/TextField";
import Autocomplete from "@material-ui/lab/Autocomplete";
import { queryApi } from "../../utils/queryApi";
import { getEtudiant, selectetudiant } from "../../redux/slices/FichePFESlice";

const EncadrantPeda = () => {
  const [danger, setDanger] = useState(false);
  const [enseignants, err] = useSelector(selectEnseignants);
  const dispatch = useDispatch();
  const [responsive, setResponsive] = useState("vertical");
  const [tableBodyHeight, setTableBodyHeight] = useState("300");
  const [tableBodyMaxHeight, setTableBodyMaxHeight] = useState("");
  const Etu = useSelector(selectetudiant);
  const [showLoader, setShowLoader] = useState(false);
  const [error, setError] = useState({ visible: false, message: "" });
  const [EtudiantsbyEns, errE] = useSelector(selectEtudiantsbyEns);

  const columns = [
    {
      name: "idEt",
      label: "Identifiant",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "nomet",
      label: "Nom",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "prenomet",
      label: "Prenom",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "telet",
      label: "Numéro Téléphone",
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
                color="danger"
                size="sm"
                onClick={() => handleModal(EtudiantsbyEns[dataIndex].idEt)}
              >
                <CIcon name="cil-pencil" />
              </CButton>
            </>
          );
        },
      },
    },
  ];
  const updateEncadrant = async (idEns, idEt) => {
    setShowLoader(true);
    const [res, err] = await queryApi(
      "responsableStage/updateEncadrantpeda?idEt=" + idEt + "&idEns=" + idEns,
      {},
      "PUT",
      false
    );
    if (err) {
      setShowLoader(false);
      setError({
        visible: true,
        message: JSON.stringify(err.errors, null, 2),
      });
    } else {
      window.location.reload();
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
  const handleClick = (id) => {
    dispatch(fetchEtudiantsbyEns(id));
  };

  const handleModal = (id) => {
    setDanger(!danger);
    dispatch(getEtudiant(id));
  };
  return (
    <>
      <CRow>
        <CCol>
          <CCard>
            <CCardHeader>
              <h5>
                Choisir l'encadrant /enseignant pour voir ces affectations
                d'encadrements pour votre option
              </h5>
            </CCardHeader>

            <CCardBody>
              <CRow>
                <CCol xs="12" md="4">
                  <Autocomplete
                    id="country-select-demo"
                    style={{ width: 300 }}
                    options={enseignants}
                    autoHighlight
                    getOptionLabel={(option) => option.idEns}
                    renderOption={(option) => (
                      <React.Fragment>
                        {option.idEns} , {option.prenomEns} {option.nomEns}
                      </React.Fragment>
                    )}
                    renderInput={(params) => (
                      <>
                        
                        <TextField
                          {...params}
                          label="Choisir un enseignant / encadrant"
                          variant="outlined"
                          inputProps={{
                            ...params.inputProps,
                            autoComplete: "new-password", // disable autocomplete and autofill
                          }}
                        />
                        <br></br> <br></br>
                        <CButton
                          className="float-right"
                          variant="outline"
                          color="danger"
                          size="sm"
                          onClick={() => handleClick(params.inputProps.value)}
                        >
                          
                          <CIcon name="cil-magnifying-glass" /> &nbsp;&nbsp;
                          Chercher ...
                        </CButton>
                      </>
                    )}
                  />
                </CCol>
                <CCol xs="12" md="8">
                  {EtudiantsbyEns ? (
                    <MUIDataTable
                      title={"Liste des étudiants"}
                      data={EtudiantsbyEns}
                      columns={columns}
                      options={options}
                    />
                  ) : (
                    <MUIDataTable
                      title={"Liste des étudiants"}
                      //data={EtudiantsbyEns}
                      columns={columns}
                      options={options}
                    />
                  )}
                </CCol>
              </CRow>
            </CCardBody>
          </CCard>
        </CCol>
        <CModal show={danger} onClose={() => setDanger(!danger)} color="dark">
          <CModalHeader closeButton>
            <CModalTitle>
              Changer l'encadrant pédagogique pour cet étudiant
            </CModalTitle>
          </CModalHeader>
          <CModalBody>
            <Autocomplete
              id="country-select-demo"
              style={{ width: 450 }}
              options={enseignants}
              autoHighlight
              getOptionLabel={(option) => option.idEns}
              renderOption={(option) => (
                <React.Fragment>
                  {option.idEns} , {option.prenomEns} {option.nomEns}
                </React.Fragment>
              )}
              renderInput={(params) => (
                <>
                  
                  <TextField
                    {...params}
                    label="Choisir un enseignant / encadrant"
                    variant="outlined"
                    inputProps={{
                      ...params.inputProps,
                      autoComplete: "new-password", // disable autocomplete and autofill
                    }}
                  />
                  <br></br> <br></br>
                  <CButton
                    className="float-right"
                    variant="outline"
                    color="danger"
                    size="sm"
                    onClick={() =>
                      updateEncadrant(params.inputProps.value, Etu.idEt)
                    }
                  >
                    
                    {showLoader && <CSpinner grow size="sm" />}
                    Valider &nbsp;&nbsp; <CIcon name="cil-check" />
                  </CButton>
                  {showLoader && (
                    <CAlert color="danger">
                      Attendre un petit peu ... Nous allons notifer à cet
                      étudiant par email ....
                    </CAlert>
                  )}
                </>
              )}
            />
          </CModalBody>
          <CModalFooter>
            <CButton color="secondary" onClick={() => setDanger(!danger)}>
              Annuler
            </CButton>
          </CModalFooter>
        </CModal>
      </CRow>
    </>
  );
};

export default EncadrantPeda;
