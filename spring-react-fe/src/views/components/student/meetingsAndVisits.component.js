import React from "react";
import HorizontalTimeline from "react-horizontal-timeline";
import AuthService from "../../services/auth.service";
import { CAlert, CCol, CRow, CCard, CCardBody, CCardFooter } from "@coreui/react";
import BellIcon from 'react-bell-icon';
import ReactTextTransition from "react-text-transition";
import "../../css/style.css";

const currentStudent = AuthService.getCurrentStudent();
const API_URL_MESP = process.env.REACT_APP_API_URL_MESP;


export default class MeetingsAndVisitsComponent extends React.Component
{
    constructor(props) {
        super(props);

        this.state = {
            curIdx: 0,
            prevIdx: -1,
            RdvVSs: [],
            value: 0, previous: 0,
            currentDay:""
        };

/*
        let requestAv = new XMLHttpRequest();
        requestAv.open("GET",API_URL_MESP + "rdvsAndVisiteStgByStudent/" + currentStudent.id, false);
        requestAv.send(null);
        let TextScroller.js = JSON.parse(requestAv.responseText);
*/
        let requestAv = new XMLHttpRequest();
        requestAv.open("GET",API_URL_MESP + "rdvsAndVisiteStgByStudent/" + currentStudent.id, false);
        requestAv.send(null);
        this.state.RdvVSs = JSON.parse(requestAv.responseText);

        // console.log('------------------------------------------TREATMENT-------------------EX------------------------------> 1');
        // console.log('-EX--->', this.state.RdvVSs);
        // console.log('---------------------------------------------TREATMENT-----------------EX------------------------------> 2');

        let currentDt = new Date();
        this.state.currentDay = currentDt.getDate() + "-" + (currentDt.getMonth() + 1) + "-" + currentDt.getFullYear();

        // console.log('-MEET--->', this.state.RdvVSs);

    }

    componentDidMount() {
        /*AuthService.rdvsAndVisiteStgByStudent(currentStudent.id).then(
            (response) => {
                //cnstructornsole.log('------------------------> TREATMENT 2: ' + new Date() + " - " + this.state.loadList);

                let stuList = this.state.RdvVSs.slice();
                for (let stu of response.data)
                {
                    console.log('------------------------> TREATMENT 2.1 : ', stu);
                    const studentObj = stu;
                    stuList.push(stu);
                }

                this.setState({
                    RdvVSs: stuList
                });

                this.state.RdvVSs = stuList;
                //console.log('------------------------> TREATMENT 3: ' + new Date() + " - " + this.state.loadList);

            },
            (error) => {
            }
        );*/

        // console.log('---------------------------------------------TREATMENT-1----------------EX------------------------------> 2');


        let requestAv = new XMLHttpRequest();
        requestAv.open("GET",API_URL_MESP + "rdvsAndVisiteStgByStudent/" + currentStudent.id, false);
        requestAv.send(null);
        this.state.RdvVSs = JSON.parse(requestAv.responseText);

        // console.log('---------------------------------------------TREATMENT-2----------------EX------------------------------> 2');

    }

    //state = { value: 0, previous: 0 };

    render()
    {
        const {curIdx, prevIdx, value, previous, RdvVSs, currentDay} = this.state;
        if(RdvVSs !== undefined && RdvVSs.length > 0)
        {
            const curStatus = RdvVSs[curIdx].type;
            const prevStatus = prevIdx >= 0 ? RdvVSs[prevIdx].type : '';

            const lol = RdvVSs[curIdx].date;
        }

        return (
            <>
                {
                    RdvVSs !== undefined && RdvVSs.length > 0 &&
                    <>
                        <CRow>
                            <CCol xs="12">
                                <div className="float-right">
                                    <span style={{color: "#cc0000", fontSize: "15px", fontWeight: "bold"}}>{RdvVSs.length}</span>
                                     &nbsp; Réunions &nbsp; <BellIcon width='30' height='30' active={true} animate={true} color="#cc0000"/>
                                </div>
                            </CCol>
                        </CRow>
                        <br/>
                        <CRow>
                            <CCol xs="2"/>
                            <CCol>
                                <div style={{width: "70%", height: "100px", margin: "0 auto", marginTop: "20px", fontSize: "15px"}}>
                                    <HorizontalTimeline styles={{background: "#ffffff", foreground: "#cc0000", outline: "#ffffff"}}
                                                        index={this.state.curIdx}
                                                        indexClick={index => {
                                                            const curIdx = this.state.curIdx;
                                                            this.setState({ curIdx: index, prevIdx: curIdx });
                                                        }}
                                                        values={RdvVSs.map(x => x.date)}
                                                        minEventPadding={20}
                                                        maxEventPadding={120}
                                                        linePadding={100}
                                                        labelWidth={100}
                                                        fillingMotion={{ stiffness: "150", damping: "25" }}
                                                        slidingMotion={{ stiffness: "150", damping: "25" }}
                                                        isTouchEnabled={true}
                                                        isKeyboardEnabled={true}
                                                        isOpenBeginning={true}
                                                        isOpenEnding={true}/>
                                </div>

                                <br/><br/>

                                {
                                    RdvVSs[curIdx].kind === "Rendez-Vous" &&
                                    <CAlert color="primary">
                                        <span style={{color: "blue", fontSize: "13px", fontWeight: "bold"}}>{RdvVSs[curIdx].kind}</span>
                                        &nbsp; en &nbsp;
                                        <span style={{color: "blue", fontSize: "12px", fontWeight: "bold", fontStyle: "italic"}}>{RdvVSs[curIdx].type}</span>
                                        <hr/>
                                        <CRow>
                                            <CCol xs="1">
                                                <span className="dateBlueIcon"/>
                                            </CCol>
                                            <CCol xs="2">
                                                <span style={{color: "blue", fontSize: "12px", fontWeight: "bold"}}>Date: </span>
                                            </CCol>
                                            <CCol xs="9">
                                                <span style={{fontSize: "12px"}}>
                                                    {RdvVSs[curIdx].date}
                                                </span>
                                            </CCol>
                                        </CRow>

                                        <CRow>
                                            <CCol xs="1">
                                                <span className="hourIcon"/>
                                            </CCol>
                                            <CCol xs="2">
                                                <span style={{color: "blue", fontSize: "12px", fontWeight: "bold"}}>Horaire: </span>
                                            </CCol>
                                            <CCol xs="9">
                                                <span style={{fontSize: "12px"}}>
                                                    {RdvVSs[curIdx].heureDebut}
                                                </span>
                                            </CCol>
                                        </CRow>

                                        <CRow>
                                            <CCol xs="1">
                                                <span className="mapBlueIcon"/>
                                            </CCol>
                                            <CCol xs="2">
                                                <span style={{color: "blue", fontSize: "12px", fontWeight: "bold"}}>Lieu: </span>
                                            </CCol>
                                            <CCol xs="9">
                                                <span style={{fontSize: "12px"}}>
                                                    {RdvVSs[curIdx].lieu}
                                                </span>
                                            </CCol>
                                        </CRow>

                                        <CRow>
                                            <CCol xs="1">
                                                <span className="noteIcon"/>
                                            </CCol>
                                            <CCol xs="2">
                                                <span style={{color: "blue", fontSize: "12px", fontWeight: "bold"}}>Observation: </span>
                                            </CCol>
                                            <CCol xs="9">
                                                <div style={{wordWrap: "break-word", fontSize: "12px"}}
                                                     className="editor"
                                                     dangerouslySetInnerHTML={{__html: RdvVSs[curIdx].observation}}>
                                                </div>
                                            </CCol>
                                        </CRow>
                                    </CAlert>
                                }

                                {
                                    RdvVSs[curIdx].kind === "Visite" &&
                                    <CAlert color="info">
                                        <span style={{color: "#0099ff", fontSize: "13px", fontWeight: "bold"}}>{RdvVSs[curIdx].kind}</span>
                                        &nbsp; en &nbsp;
                                        <span style={{color: "#0099ff", fontSize: "12px", fontWeight: "bold", fontStyle: "italic"}}>{RdvVSs[curIdx].type}</span>
                                        <hr/>
                                        <CRow>
                                            <CCol xs="1">
                                                <span className="dateLightBlueIcon"/>
                                            </CCol>
                                            <CCol xs="2">
                                                <span style={{color: "#0099ff", fontSize: "12px", fontWeight: "bold"}}>Date: </span>
                                            </CCol>
                                            <CCol xs="9">
                                                <span style={{fontSize: "12px"}}>
                                                    {RdvVSs[curIdx].date}
                                                </span>
                                            </CCol>
                                        </CRow>

                                        <CRow>
                                            <CCol xs="1">
                                                <span className="hourIcon"/>
                                            </CCol>
                                            <CCol xs="2">
                                                <span style={{color: "#0099ff", fontSize: "12px", fontWeight: "bold"}}>Horaire: </span>
                                            </CCol>
                                            <CCol xs="9">
                                                <span style={{fontSize: "12px"}}>
                                                    {RdvVSs[curIdx].heureDebut} - {RdvVSs[curIdx].heureFin}
                                                </span>
                                            </CCol>
                                        </CRow>

                                        <CRow>
                                            <CCol xs="1">
                                                <span className="mapLightBlueIcon"/>
                                            </CCol>
                                            <CCol xs="2">
                                                <span style={{color: "#0099ff", fontSize: "12px", fontWeight: "bold"}}>Lieu: </span>
                                            </CCol>
                                            <CCol xs="9">
                                                <span style={{fontSize: "12px"}}>
                                                    {RdvVSs[curIdx].lieu}
                                                </span>
                                            </CCol>
                                        </CRow>

                                        <CRow>
                                            <CCol xs="1">
                                                <span className="noteIcon"/>
                                            </CCol>
                                            <CCol xs="2">
                                                <span style={{color: "#0099ff", fontSize: "12px", fontWeight: "bold"}}>Observation: </span>
                                            </CCol>
                                            <CCol xs="9">
                                                <div style={{wordWrap: "break-word", fontSize: "12px"}}
                                                     className="editor"
                                                     dangerouslySetInnerHTML={{__html: RdvVSs[curIdx].observation}}>
                                                </div>
                                            </CCol>
                                        </CRow>
                                    </CAlert>
                                }



                            </CCol>
                            <CCol xs="2"/>
                        </CRow>
                    </>
                }
                {
                    (RdvVSs === undefined || RdvVSs.length === 0) &&
                    <CRow>
                        <CCol md="2"/>
                        <CCol md="8">
                            <br/><br/>
                            <CCard accentColor="danger">
                                <CCardBody>
                                    <center>
                                        <br /><br />
                                        Vous n'avez pas encore aucun &nbsp;
                                        <span className="clignoteRed">Rendez-Vous</span>
                                        &nbsp; et/ou &nbsp;
                                        <span className="clignoteRed">Visite</span> &nbsp;.
                                        <br /><br />
                                        <br />
                                    </center>
                                </CCardBody>
                                <CCardFooter>
                                    <span style={{color: "#666666", fontSize: "11px", fontStyle: "italic"}} className="float-right">
                                      Today is {currentDay}
                                    </span>
                                </CCardFooter>
                            </CCard>
                        </CCol>
                        <CCol md="2"/>
                    </CRow>
                }
            </>
        );
    }
}
