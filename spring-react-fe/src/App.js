import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import './scss/style.scss';
//import Home from "./views/components/responsible/home.component";

import 'popper.js/dist/popper.min.js';
import 'bootstrap/dist/js/bootstrap.min.js';
// Styles
//import 'bootstrap/dist/css/bootstrap.min.css';


const loading = (
  <div className="pt-3 text-center">
    <div className="sk-spinner sk-spinner-pulse"></div>
  </div>
)

// Containers
const TheLayout = React.lazy(() => import('./containers/TheLayout'));

// Pages
const Login = React.lazy(() => import('./views/pages/login/Login'));

const RenewMyTeacherPassword = React.lazy(() => import('./views/pages/login/teacher/RenewMyTeacherPassword'));
const RenewMyStudentPassword = React.lazy(() => import('./views/pages/login/student/RenewMyStudentPassword'));
const RenewMyPedagogicalCoordinatorPassword = React.lazy(() => import('./views/pages/login/pedagogicalCoordinator/RenewMyPedagogicalCoordinatorPassword'));

const Student = React.lazy(() => import('./views/pages/login/student/Student'));
const ResponsableServiceStage = React.lazy(() => import('./views/pages/login/responsableServiceStage/ResponsableServiceStage'));
const Teacher = React.lazy(() => import('./views/pages/login/teacher/Teacher'));
const PedagogicalCoordinator = React.lazy(() => import('./views/pages/login/pedagogicalCoordinator/PedagogicalCoordinator'));

const ForgotStudentPassword = React.lazy(() => import('./views/pages/login/student/ForgotStudentPassword'));
const ForgotResponsableServiceStagePassword = React.lazy(() => import('./views/pages/login/responsableServiceStage/ForgotResponsableServiceStagePassword'));
const ForgotTeacherPassword = React.lazy(() => import('./views/pages/login/teacher/ForgotTeacherPassword'));
const ForgotPedagogicalCoordinatorPassword = React.lazy(() => import('./views/pages/login/pedagogicalCoordinator/ForgotPedagogicalCoordinatorPassword'));

class App extends Component
{
    render() {
      return (
    	<Router basename={'/'}>
              <React.Suspense fallback={loading}>
                <Switch>
                  <Route exact path="/" name="Login Page" render={props => <Login {...props}/>} />

                  <Route exact path="/student" name="Student Page" render={props => <Student {...props}/>} />
                  <Route exact path="/responsableServiceStage" name="ResponsableServiceStage Page" render={props => <ResponsableServiceStage {...props}/>} />
                  <Route exact path="/teacher" name="Teacher Page" render={props => <Teacher {...props}/>} />
                  <Route exact path="/pedagogicalCoordinator" name="PedagogicalCoordinator Page" render={props => <PedagogicalCoordinator {...props}/>} />

                  <Route exact path="/forgotStudentPassword" name="ForgotStudentPassword Page" render={props => <ForgotStudentPassword {...props}/>} />
                  <Route exact path="/forgotResponsableServiceStagePassword" name="ForgotResponsableServiceStagePassword Page" render={props => <ForgotResponsableServiceStagePassword {...props}/>} />
                  <Route exact path="/forgotTeacherPassword" name="ForgotTeacherPassword Page" render={props => <ForgotTeacherPassword {...props}/>} />
                  <Route exact path="/forgotPedagogicalCoordinatorPassword" name="ForgotPedagogicalCoordinatorPassword Page" render={props => <ForgotPedagogicalCoordinatorPassword {...props}/>} />
                  
                  <Route exact path="/renewMyTeacherPassword" name="Renew My Password" render={props => <RenewMyTeacherPassword {...props}/>} />
                  <Route exact path="/renewMyStudentPassword" name="Renew My Password" render={props => <RenewMyStudentPassword {...props}/>} />
                  <Route exact path="/renewMyPedagogicalCoordinatorPassword" name="Renew My Password" render={props => <RenewMyPedagogicalCoordinatorPassword {...props}/>} />

                  <Route path="/" name="Home" render={props => <TheLayout {...props}/>} />
                </Switch>
              </React.Suspense>
          </Router>
      );
    }
}

export default App;
