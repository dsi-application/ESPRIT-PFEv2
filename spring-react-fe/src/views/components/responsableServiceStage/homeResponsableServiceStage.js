import React, {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import {Link} from 'react-router-dom';
import {
	fetchRefusedReports,
	fetchStudentDoneSTNStat,
	fetchStudentPlanifSTNStat,
	fetchStudentToSTNStat,
	fetchUploadedReports,
	fetchValidatedReports,
	selectDepotStat,
	selectNbrRefusedReports,
	selectNbrUploadedReports,
	selectNbrValidatedReports,
	selectRefusedDepotStat,
	selectStudentsDoneStat,
	selectStudentsPLanifStat,
	selectStudentsToStat,
	selectUploadedDepotStat,
	selectValidatedDepotStat
} from "../../../redux/slices/DepotSlice";
import {Wave} from "react-animated-text";

import "../../css/style.css";

import {
	CButton,
	CCard,
	CCardBody,
	CCol,
	CContainer,
	CRow,
	CTooltip,
	CWidgetBrand,
	CWidgetProgressIcon
} from "@coreui/react";
import MainChartExample from '../../utils/charts/MainChartExample';

import {
	fectchServiceStat,
	fectchServiceStatEM,
	fectchServiceStatGC,
	selectServiceStat,
	selectServiceStatEM,
	selectServiceStatGC
} from "../../../redux/slices/StatSlice";

// npm install --save-dev @iconify/react @iconify-icons/mdi
import {Icon} from '@iconify/react';
import googleCirclesGroup from '@iconify-icons/mdi/google-circles-group';
import {withStyles} from '@material-ui/core/styles';
import TheSidebar from "../../../containers/TheSidebar";
import {
	selectNbrDemandesAnnulationConvention, selectNbrDemandesAnnulationConventionNotTreated,
	selectNbrDepositedConventions, selectNbrValidatedConventions,
} from "../../../redux/slices/ConventionSlice";


const useStyles = theme => ({
	root: {
		width: 'fit-content',
		border: `1px solid ${theme.palette.divider}`,
		borderRadius: theme.shape.borderRadius,
		backgroundColor: theme.palette.background.paper,
		color: theme.palette.text.secondary,
		'& svg': {
			margin: theme.spacing(1.5),
		},
		'& hr': {
			margin: theme.spacing(0, 0.5),
		},
	},
});


const HomeResponsableServiceStage = () => {
	const dispatch = useDispatch();
	const [depotStat, errds] = useSelector(selectDepotStat);
	const [uploadedDepotStat, erruds] = useSelector(selectUploadedDepotStat);
	const [validatedDepotStat, errvds] = useSelector(selectValidatedDepotStat);
	const [refusedDepotStat, errrds] = useSelector(selectRefusedDepotStat);
	const [soutenancesNonPlanified, errsts] = useSelector(selectStudentsToStat);
	const [soutenancesPlanified, errsps] = useSelector(selectStudentsPLanifStat);
	const [soutenancesNotified, errsds] = useSelector(selectStudentsDoneStat);

	const [nbUploadedReports, errnur] = useSelector(selectNbrUploadedReports);
	const [nbValidatedReports, errnvr] = useSelector(selectNbrValidatedReports);
	const [nbIncompletedReports, errnir] = useSelector(selectNbrRefusedReports);

	const [ServiceStat, err] = useSelector(selectServiceStat);
	const [ServiceStatEM, err1] = useSelector(selectServiceStatEM);
	const [ServiceStatGC, err2] = useSelector(selectServiceStatGC);

	const [nbDemandesAnnulationConvention, errndac] = useSelector(selectNbrDemandesAnnulationConventionNotTreated);
	const [nbDepositedConventions, errndc] = useSelector(selectNbrDepositedConventions);
	const [nbValidatedConventions, errnvc] = useSelector(selectNbrValidatedConventions);

	const ServiceStatStatus = useSelector((state) => state.persistedReducer.stat.ServiceStatStatus);
	const ServiceStatStatusEM = useSelector((state) => state.persistedReducer.stat.ServiceStatStatusEM);
	const ServiceStatStatusGC = useSelector((state) => state.persistedReducer.stat.ServiceStatStatusGC);

	useEffect(() => {
		dispatch(fetchStudentToSTNStat());
		dispatch(fetchStudentPlanifSTNStat());
		dispatch(fetchStudentDoneSTNStat());
		dispatch(fetchUploadedReports());
		dispatch(fetchValidatedReports());
		dispatch(fetchRefusedReports());
		dispatch(fectchServiceStat());
		dispatch(fectchServiceStatEM());
		dispatch(fectchServiceStatGC());
	}, [nbUploadedReports, nbValidatedReports, nbIncompletedReports]);

	return (
		<>

			<TheSidebar data1={soutenancesNonPlanified} data3={soutenancesPlanified} data2={soutenancesNotified}
						dataUR={nbUploadedReports} dataVR={nbValidatedReports} dataIR={nbIncompletedReports}
						dataDAC={nbDemandesAnnulationConvention} dataDC={nbDepositedConventions} dataVC={nbValidatedConventions}/>

			<>
				{ServiceStatStatus === "loading" || ServiceStatStatus === "noData" ||
				ServiceStatStatusEM === "loading" || ServiceStatStatusEM === "noData" ||
				ServiceStatStatusGC === "loading" || ServiceStatStatusGC === "noData" ? (
					<>
						<CRow>
							<CCol xs="12">
								<center>
									<br/><br/>
									<span className="waitIcon"/>
									<br></br>
								</center>
							</CCol>
						</CRow>
					</>
				) : (
					<>
						<CRow>
							<CCol md="6">
				  				<span className="statusUnityConv">
									<Wave style={{fontSize: "50px"}} text="État Traitement Conventions" effect="stretch" effectChange={3.3}/>
				  				</span>
								<CContainer>
									<CRow>
										<CCol md="4" className="colPadding">
										<span className="statusUnityConvSubTitle">
										  Info & Télécoms
										</span>
											<CWidgetBrand color="twitter"
														  style={{fontSize:'11px'}}
														  rightHeader={ServiceStat[0].nombreConventionTraite}
														  rightFooter="Validés"
														  leftHeader={ServiceStat[0].nombreConventionAnnuler}
														  leftFooter="Annulés">

												<CCol md="9" className="colPadding">
													<br/>
													Nbr. Total :
													<br/>
													Unité Non Traitée :
													<br/><br/>
												</CCol>

												<CCol md="3" className="colPadding">
													<br/>
													{ServiceStat[0].nombreAllConvention}
													<br/>
													{ServiceStat[0].nombreConventionNontraite}
													<br/><br/>
												</CCol>
											</CWidgetBrand>
										</CCol>
										<CCol md="4" className="colPadding">
							<span className="statusUnityConvSubTitle">
							  Électro-Mécanique
							</span>
											<CWidgetBrand color="twitter"
														  style={{fontSize:'11px'}}
														  rightHeader={ServiceStatEM[0].nombreConventionTraite}
														  rightFooter="Validés"
														  leftHeader={ServiceStatEM[0].nombreConventionAnnuler}
														  leftFooter="Annulés">

												<CCol md="9" className="colPadding">
													<br/>
													Nbr. Total :
													<br/>
													Unité Non Traitée :
													<br/><br/>
												</CCol>

												<CCol md="3" className="colPadding">
													<br/>
													{ServiceStatEM[0].nombreAllConvention}
													<br/>
													{ServiceStatEM[0].nombreConventionNontraite}
													<br/><br/>
												</CCol>
											</CWidgetBrand>
										</CCol>
										<CCol md="4" className="colPadding">
							<span className="statusUnityConvSubTitle">
							  Génie Civil
							</span>
											<CWidgetBrand color="twitter"
														  style={{fontSize:'11px'}}
														  rightHeader={ServiceStatGC[0].nombreConventionTraite}
														  rightFooter="Validés"
														  leftHeader={ServiceStatGC[0].nombreConventionAnnuler}
														  leftFooter="Annulés">

												<CCol md="9" className="colPadding">
													<br/>
													Nbr. Total :
													<br/>
													Unité Non Traitée :
													<br/><br/>
												</CCol>

												<CCol md="3" className="colPadding">
													<br/>
													{ServiceStatGC[0].nombreAllConvention}
													<br/>
													{ServiceStatGC[0].nombreConventionNontraite}
													<br/><br/>
												</CCol>
											</CWidgetBrand>
										</CCol>
									</CRow>
								</CContainer>
							</CCol>

							<CCol md="6">
								  <span className="statusUnity">
									  <Wave style={{fontSize: "50px"}} text="État Traitement Avenants" effect="stretch"
											effectChange={3.5}/>
								  </span>

								  <CContainer>
										<CRow>
											<CCol md="4" className="colPadding">
									<span className="statusUnityConvSubTitleAvn">
									  Info & Télécoms
									</span>
												<CWidgetBrand color="facebook"
															  style={{fontSize:'11px'}}
															  rightHeader={ServiceStat[0].nombreAvenantTraite}
															  rightFooter="Validés">
													<CCol md="9" className="colPadding">
														<br/>
														Nbr. Total :
														<br/>
														Unité Non Traitée :
														<br/><br/>
													</CCol>

													<CCol md="3" className="colPadding">
														<br/>
														{ServiceStat[0].nombreAllAvenant}
														<br/>
														{ServiceStat[0].nombreAvenantNonTraite}
														<br/><br/>
													</CCol>
												</CWidgetBrand>
											</CCol>
											<CCol md="4" className="colPadding">
									<span className="statusUnityConvSubTitleAvn">
									  Électro-Mécanique
									</span>
												<CWidgetBrand color="facebook"
															  style={{fontSize:'11px'}}
															  rightHeader={ServiceStatEM[0].nombreAvenantTraite}
															  rightFooter="Validés">
													<CCol md="9" className="colPadding">
														<br/>
														Nbr. Total :
														<br/>
														Unité Non Traitée :
														<br/><br/>
													</CCol>

													<CCol md="3" className="colPadding">
														<br/>
														{ServiceStatEM[0].nombreAllAvenant}
														<br/>
														{ServiceStatEM[0].nombreAvenantNonTraite}
														<br/><br/>
													</CCol>
												</CWidgetBrand>
											</CCol>
											<CCol md="4" className="colPadding">
									<span className="statusUnityConvSubTitleAvn">
									  Génie Civil
									</span>
												<CWidgetBrand color="facebook"
															  style={{fontSize:'11px'}}
															  rightHeader={ServiceStatGC[0].nombreAvenantTraite}
															  rightFooter="Validés">
													<CCol md="9" className="colPadding">
														<br/>
														Nbr. Total :
														<br/>
														Unité Non Traitée :
														<br/><br/>
													</CCol>

													<CCol md="3" className="colPadding">
														<br/>
														{ServiceStatGC[0].nombreAllAvenant}
														<br/>
														{ServiceStatGC[0].nombreAvenantNonTraite}
														<br/><br/>
													</CCol>
												</CWidgetBrand>
											</CCol>
										</CRow>
									</CContainer>
							</CCol>
						</CRow>
					</>
				)}
			</>

			<CRow>
				<CCol md="5">
					<CContainer>
						<CRow>
							<CCol md="6">
								<span className="clignoteBlack">État Traitement Dépôts</span>
								<CWidgetBrand color="gradient-dark"
											  style={{fontSize:'11px'}}
											  rightHeader={nbValidatedReports} rightFooter="Validés"
											  leftHeader={nbIncompletedReports} leftFooter="Incomplets">
									<CCol md="9" className="colPadding">
										<br/>
										Nbr. Total :
										<br/>
										Unité Non Traitée :
										<br/><br/>
									</CCol>

									<CCol md="3" className="colPadding">
										<br/>
										{nbUploadedReports + nbValidatedReports + nbIncompletedReports}
										<br/>
										{nbUploadedReports}
										<br/><br/>
									</CCol>
								</CWidgetBrand>
							</CCol>
						<CCol md="6">
							<CWidgetProgressIcon header={soutenancesNonPlanified}
												 text="Stns Non Planifiées"
												 color="gradient-danger" height={5555}
												 inverse>
								<Link to="/planifySoutenance">
									<CButton shape="pill"
											 color="danger"
											 size="sm" height="10"
											 aria-expanded="true">
										<CTooltip content="Go to Soutenances Non Planifiées" placement="top">
											<Icon icon={googleCirclesGroup} color="black" width="20" height="25"/>
										</CTooltip>
									</CButton>
								</Link>
							</CWidgetProgressIcon>
						</CCol>
					</CRow>
					<CRow>
						<CCol md="6">
							<CWidgetProgressIcon header={soutenancesPlanified}
												 text="Soutenances Planifiées"
												 color="gradient-warning"
												 inverse height="10">
								<Link to="/soutenancesPlanifiées">
									<CButton shape="pill"
											 color="warning"
											 size="sm" height="10"
											 aria-expanded="true">
										<CTooltip content="Go to Soutenances Planifiées" placement="top">
											<Icon icon={googleCirclesGroup} color="black" width="20" height="25"/>
										</CTooltip>
									</CButton>
								</Link>
							</CWidgetProgressIcon>
						</CCol>
						<CCol md="6">
							<CWidgetProgressIcon header={soutenancesNotified}
												 text="Soutenances Notifiées"
												 color="gradient-success"
												 inverse>
								<Link to="/soutenancesNotifiées">
									<CButton shape="pill"
											 color="success"
											 size="sm" height="10"
											 aria-expanded="true">
										<CTooltip content="Go to Soutenances Notifiées" placement="top">
											<Icon icon={googleCirclesGroup} color="black" width="20" height="25"/>
										</CTooltip>
									</CButton>
								</Link>
							</CWidgetProgressIcon>
						</CCol>
					</CRow>
					</CContainer>
				</CCol>
				<CCol md="7">
					<CCard>
						<CCardBody>
							<h4 id="traffic" className="card-title mb-0">Soutenances Effectuées par Date</h4>
							<div className="small text-muted">
								<span className="clignoteGrey">
									Session Septembre 2022
								</span>
							</div>

							<MainChartExample style={{height: '225px', marginTop: '20px'}}/>
						</CCardBody>
					</CCard>
				</CCol>
			</CRow>
		</>
	);
};

export default withStyles(useStyles)(HomeResponsableServiceStage)

