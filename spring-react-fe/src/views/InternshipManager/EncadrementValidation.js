import React, {useState} from "react";
import {useDispatch, useSelector} from "react-redux";

import "moment/locale/fr";
import {CButton, CCard, CCardBody, CCol, CRow, CSpinner, CWidgetProgressIcon,} from "@coreui/react";
import CIcon from "@coreui/icons-react";
import {
  fetchEncadrementValidation,
  populateEncadrementValidationNull,
  selectAllAnnee,
  selectEncadrementValidation,
} from "../../redux/slices/ListingSlice";
import MUIDataTable from "mui-datatables";
import {FormGroup, TextField} from "@material-ui/core";
import Autocomplete from "@material-ui/lab/Autocomplete";

const EncadrementValidation = () => {
  const [EncadrementValidation, err] = useSelector(selectEncadrementValidation);
  const dispatch = useDispatch();
  const [responsive, setResponsive] = useState("vertical");
  const [tableBodyHeight, setTableBodyHeight] = useState("300");
  const [tableBodyMaxHeight, setTableBodyMaxHeight] = useState("");
  const [AllAnnee, err4] = useSelector(selectAllAnnee);
  const EncadrementValidationsStatus = useSelector(
      (state) => state.persistedReducer.listing.EncadrementValidationsStatus
  );
  const [values, setValues] = React.useState([]);
  let list = [];
  if (AllAnnee.length !== 0) {
    AllAnnee.map((s) => {
      var obj = {
        valueAnnee: s[0],
        label: s[0] + "-" + s[1],
      };
      list.push(obj);
    });
  }
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
        noMatch: "Désolé, aucune donnée correspondante n'a été trouvée.",
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
  const columns = [
    {
      name: "idEns",
      label: "Identifiant enseignant",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "nomEns",
      label: "Nom enseignant",
      options: {
        filter: true,
        sort: true,
      },
    },

    {
      name: "nbrencadrement",
      label: "Nombre d'encadrement",
      options: {
        filter: true,
        sort: true,
      },
    },
  ];
  const handleChange = (e) => {
    dispatch(fetchEncadrementValidation(e));
  };
  const handleRest = (e) => {
    setValues([]);
    dispatch(populateEncadrementValidationNull());
  };
  return (
      <>
        <CRow>
          <CCol>
            <CCard>
              <CCardBody>
                <CCol md="12">
                  <CWidgetProgressIcon
                      // className="float-right"
                      header={EncadrementValidation.length}
                      text="    Nombre Total Enseignants"
                      color="gradient-danger"
                      inverse
                  >
                    <CIcon name="cil-speedometer" height="36"/>
                  </CWidgetProgressIcon>
                </CCol>
                <br></br>
                <FormGroup style={{flexDirection: "row"}}>
                  <Autocomplete
                      //forcePopupIcon={false}
                      value={values}
                      id="country-select-demo"
                      style={{width: 1100}}
                      options={list}
                      autoHighlight
                      getOptionLabel={(option) => option.valueAnnee}
                      renderOption={(option) => (
                          <React.Fragment>{option.label}</React.Fragment>
                      )}
                      renderInput={(params) => (
                          <>

                            <CRow>
                              <CCol md="2"/>
                              <CCol sm="12" md="6">
                                <center>
                                  <TextField
                                      id="combo-box-demo"
                                      {...params}
                                      label="Choisir une Année Universitaire"
                                      variant="outlined"
                                      fullWidth
                                      inputProps={{
                                        ...params.inputProps,
                                        autoComplete: "new-password", // disable autocomplete and autofill
                                      }}
                                  />
                                </center>
                              </CCol>

                              <CCol sm="12" md="4">
                                <center>
                                  <div
                                      style={{
                                        display: "inline",
                                      }}
                                  >
                                    <CButton
                                        //  className="float-right"
                                        variant="outline"
                                        color="dark"
                                        size="lg"
                                        type="submit"
                                        onClick={() =>
                                            handleChange(params.inputProps.value)
                                        }
                                    >
                                      <CIcon name="cil-magnifying-glass"/>
                                      &nbsp; Chercher &nbsp;
                                      {EncadrementValidationsStatus === "loading" && (
                                          <CSpinner color="secondary" size="sm"/>
                                      )}
                                    </CButton>
                                    &nbsp;
                                    <CButton
                                        //    className="float-left"
                                        variant="outline"
                                        color="danger"
                                        size="lg"
                                        type="submit"
                                        //   type="reset"
                                        onClick={handleRest}
                                    >

                                      <CIcon name="cil-x"/> &nbsp; Annuler
                                    </CButton>
                                  </div>
                                </center>
                              </CCol>
                            </CRow>
                          </>
                      )}
                  />
                </FormGroup>
                <br></br>
                {EncadrementValidationsStatus === "loading" ? (
                    <>
                      <CRow>
                        <CCol xs="12">
                          <center>
                            <br/><br/><br/><br/><br/>
                            <span className="waitIcon"/>
                          </center>
                        </CCol>
                      </CRow>
                      <br/><br/><br/><br/><br/>
                    </>
                ) : (
                    <>
                      <CRow>
                        <CCol xs="12">
                          <br/>
                          &nbsp;
                          <span style={{color: "#b30000", fontSize: "14px", fontWeight: "bold"}}>Nombre d'Encadrements des Enseignants par Année</span>
                        </CCol>
                      </CRow>
                      <br/>
                      {EncadrementValidation ? (
                          <MUIDataTable
                              data={EncadrementValidation}
                              columns={columns}
                              options={options}
                          />
                      ) : (
                          <MUIDataTable
                              columns={columns}
                              options={options}
                          />
                      )}
                    </>
                )}
              </CCardBody>
            </CCard>
          </CCol>
        </CRow>
      </>
  );
};

export default EncadrementValidation;
