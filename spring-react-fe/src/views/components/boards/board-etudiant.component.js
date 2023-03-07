import React, { Component } from "react";

import AuthService from "../../services/auth.service";
import AddFichePfe from "../addFichePfe.component";
import ModifyFichePfe from "../modifyFichePfe.component";
import CancelFichePFE from "../cancelFichePFE.component";
import ConsultFichePfe from "../consultFichePfe.component";
import ManageConvention from "../manageConvention.component";
import UploadReport from "../uploadReport.component";
import AddAvenant from "../addAvenant.component";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";

const currentResponsible = AuthService.getCurrentResponsible();

export default class BoardEtudiant extends Component {
  constructor(props) {
    super(props);

    this.state = {
      content: "",
      fichePFEToBeUpdated: {},
      supervisors: [],
      convention: {},
      idRap: 0,
    };

    var requestfp = new XMLHttpRequest();
    requestfp.open(
      "GET",
      "http://localhost:1236/api/auth/fichePFE/" + currentResponsible.id,
      false
    ); // return axios.get(API_URL + 'user/' + code);
    requestfp.send(null);
    this.state.fichePFEToBeUpdated = JSON.parse(requestfp.responseText);

    if (this.state.fichePFEToBeUpdated) {
      // console.log("====================> YES: " + currentResponsible.id + " - " + this.state.fichePFEToBeUpdated.idFiche);
    }
    if (!this.state.fichePFEToBeUpdated) {
      // console.log("====================> NO");
    }

    if (this.state.fichePFEToBeUpdated.idFiche === undefined) {
      // console.log("====================> LOL");
    }

    var requesta = new XMLHttpRequest();
    requesta.open(
      "GET",
      "http://localhost:1236/api/auth/convention/" + currentResponsible.id,
      false
    ); // return axios.get(API_URL + 'user/' + code);
    requesta.send(null);
    let convId = JSON.parse(requesta.responseText);
    

    if (convId !== 0) {
      var requestb = new XMLHttpRequest();
      requestb.open(
        "GET",
        "http://localhost:1236/api/auth/conventionEntity/" + convId,
        false
      ); // return axios.get(API_URL + 'user/' + code);
      requestb.send(null);
      this.state.convention = JSON.parse(requestb.responseText);
      // console.log("------------init------------- convention entity: " + this.state.convention.societe);
    }

    var requestdr = new XMLHttpRequest();
    requestdr.open(
      "GET",
      "http://localhost:1236/api/auth/files/" + currentResponsible.id,
      false
    ); // return axios.get(API_URL + 'user/' + code);
    requestdr.send(null);

    if (JSON.parse(requestdr.responseText).length > 0) {
      this.state.idRap = 1;
    }

    // console.log("------------init------------- idRap: " + this.state.idRap);
  }

  componentDidMount() {
    AuthService.getEtudiantBoard().then(
      (response) => {
        this.setState({
          content: response.data,
        });
      },
      (error) => {
        this.setState({
          content:
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString(),
        });
      }
    );
  }

  render() {
    return (
      <Router>
        <div className="container">
          <header className="jumbotron">
            <h3>{this.state.content}</h3>
          </header>
        </div>
        <div>
          <ul>
            <li>
              <Link to={"/manageConvention"}>Manage Convention</Link>
            </li>
            {this.state.fichePFEToBeUpdated.idFiche === undefined && (
              <li>
                <Link to={"/addFichePFE"}>Add Plan Travail</Link>
              </li>
            )}

            {
              <li>
                <Link to={"/modifyFichePfe"}>Modify Plan Travail</Link>
              </li>
            }

            {
              <li>
                <Link to={"/consultFichePfe"}>Consult Plan Travail</Link>
              </li>
            }

            {this.state.fichePFEToBeUpdated.idFiche !== 0 && (
              <li>
                <Link to={"/cancelFichePFE"}>
                  Demande Annulation/Modification Plan Travail
                </Link>
              </li>
            )}

            {this.state.convention.traiter && (
              <li>
                <Link to={"/addAvenant"}>Add Avenant</Link>
              </li>
            )}

            {this.state.idRap === 0 && (
              <li>
                <Link to={"/uploadReport"}>Upload Report</Link>
              </li>
            )}
          </ul>
          <hr />

          <Switch>
            <Route path="/manageConvention" component={ManageConvention} />
            <Route path="/addFichePfe" component={AddFichePfe} />
            <Route path="/modifyFichePfe" component={ModifyFichePfe} />
            <Route path="/consultFichePfe" component={ConsultFichePfe} />
            <Route path="/cancelFichePFE" component={CancelFichePFE} />
            <Route path="/addAvenant" component={AddAvenant} />
            <Route path="/uploadReport" component={UploadReport} />
          </Switch>
        </div>
      </Router>
    );
  }
}
