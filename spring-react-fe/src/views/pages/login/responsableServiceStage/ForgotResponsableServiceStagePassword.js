import React, { Component } from "react";
import Form from "react-validation/build/form";
import CheckButton from "react-validation/build/button";
import { Link } from 'react-router-dom';
import esprit from '../../../images/esprit.gif';
import { CButton, CTooltip, CCard, CCardBody, CCardGroup, CCol, CContainer, CForm, CInput, CInputGroup, CInputGroupPrepend, CInputGroupText, CRow } from '@coreui/react';
import CIcon from '@coreui/icons-react'
import { freeSet } from '@coreui/icons';

import AuthService from "../../../services/auth.service";
import '../../../css/style.css';

const required = value => {
  if (!value) {
    return (
      <div className="alert alert-danger" role="alert">
        This field is required!
      </div>
    );
  }
};

//
export default class ForgotResponsableServiceStagePassword extends Component
{
    constructor(props)
    {
        super(props);
        this.handleLogin = this.handleLogin.bind(this);
        this.onChangeUsername = this.onChangeUsername.bind(this);
        this.onChangePassword = this.onChangePassword.bind(this);

        this.state = {
          username: "",
          password: "",
          loading: false,
          message: ""
        };
    }

    onChangeUsername(e) {
      this.setState({
        username: e.target.value
      });
    }

    onChangePassword(e) {
      this.setState({
        password: e.target.value
      });
    }

    handleLogin(e)
    {
        e.preventDefault();

        this.setState({
            message: "",
            loading: true
        });

        this.form.validateAll();

        if (this.checkBtn.context._errors.length === 0)
        {
              // console.log('--------------------> TRY');
              AuthService.forgotResponsableServiceStagePassword(this.state.username, this.state.password).then(
                  response => 
                            {
                           
                                // console.log('Renew Password -------------------------> ** Sign-In 0');
                                sessionStorage.setItem("responsableServiceStage", JSON.stringify(response.data));
                                if(response.data)
                                this.props.history.push("/renewResponsableServiceStagePassword");
                           

                                // console.log('Renew Password -------------------------> ** Sign-In 1');
                                // console.log(response.data)
                                // console.log('Renew Password -------------------------> ** Sign-In 2');

                                
                                window.location.reload();

                                return response.data;
                            },
                            error => 
                            {
                                // console.log('Renew Password -------------- Error Authentication ');
                                // console.log(error);

                                // console.log('Renew Password --------------------> FAILED');
                                const resMessage =
                                  (error.response &&
                                    error.response.data &&
                                    error.response.data.message) ||
                                  error.message ||
                                  error.toString();

                                this.setState({
                                  loading: false,
                                  message: resMessage
                                });
                                // this.logout();
                            }
            )
        } else {
          // console.log('--------------------> LOAD: false');
          this.setState({
            loading: false
          });
        }
    }

    render()
    {
        return (
          <div>
              <div className="c-app c-default-layout flex-row align-items-center">
                  <CContainer>
                    <CRow className="justify-content-center">
                      <CCol md="8">
                        <CCardGroup>
                          <CCard className="p-4">
                            <CCardBody>
                              <CForm>
                                <h1>Oublier Mot de Passe</h1>
                                <Form
                                  onSubmit={this.handleLogin}
                                  ref={c => {
                                    this.form = c;
                                  }}
                                >
                                <p className="text-muted">Connectez-vous à votre compte</p>
                                <br/><br/>
                                <CInputGroup className="mb-3">
                                  <CInputGroupPrepend>
                                    <CInputGroupText>
                                      <CIcon name="cil-user" />
                                    </CInputGroupText>
                                  </CInputGroupPrepend>
                                  <CInput placeholder="Nom Utilisateur" autoComplete="username" 
                                          type="text" className="form-control" name="username"
                                          value={this.state.username} onChange={this.onChangeUsername} 
                                          validations={[required]}/>
                                </CInputGroup>
                                <CInputGroup className="mb-4">
                                  <CInputGroupPrepend>
                                    <CInputGroupText>
                                      <CIcon name="cil-lock-locked" />
                                    </CInputGroupText>
                                  </CInputGroupPrepend>
                                  <CInput placeholder="Mot de Passe" autoComplete="current-password" 
                                          type="password" className="form-control" name="password"
                                          value={this.state.password} onChange={this.onChangePassword}
                                          validations={[required]}/>
                                </CInputGroup>
                                <CRow>
                                  <CCol xs="6">
                                    <div className="form-group">
                                      <button
                                        className="btn btn-primary btn-block"
                                        disabled={this.state.loading}
                                      >
                                        {this.state.loading && (
                                          <span className="spinner-border spinner-border-sm"></span>
                                        )}
                                        <span>Continuer</span>
                                      </button>
                                    </div>

                                    {this.state.message && (
                                      <div className="form-group">
                                        <div className="alert alert-danger" role="alert">
                                          {this.state.message}
                                        </div>
                                      </div>
                                    )}
                                    <CheckButton
                                      style={{ display: "none" }}
                                      ref={c => {
                                        this.checkBtn = c;
                                      }}
                                    />
                                  </CCol>

                                  <CCol xs="6" className="text-right">
                                    <Link to="/responsableServiceStage">
                                      <CButton color="link" className="px-0">Connecter</CButton>
                                    </Link>
                                  </CCol>

                                </CRow>
                                 </Form>
                              </CForm>
                            </CCardBody>
                          </CCard>
                          <CCard className="text-white bg-light py-4">
                            <CCardBody className="text-center">
                              <div>
                                <CRow>
                                  <CCol xs={8}></CCol>
                                    <CCol xs={4}>
                                      <img src={esprit} alt="logo" title="Welcome to Esprit" height={50} width={100} />
                                  </CCol>
                                </CRow>
                                
                                <br/><br/>
                                <span className="platformTitle">Gestion des Stages</span>

                                <br/>
                                <p className="userSpace">Espace Responsable Service Stage</p>

                                <br/>
                                <Link to="/">
                                <CButton shape="pill" color="danger">
                                    <CTooltip content="Page d'Accueil" placement="top">
                                      <CIcon content={freeSet.cilHome} />
                                    </CTooltip>
                                </CButton>
                              </Link>
                              </div>
                            </CCardBody>
                          </CCard>
                        </CCardGroup>
                      </CCol>
                    </CRow>
                  </CContainer>
              </div>
          </div>  
        );
    }
}
