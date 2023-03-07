import React, { useState } from 'react';

import {Panel} from "rsuite";
import {Button, Steps} from "antd";
import CIcon from "@coreui/icons-react";
import { freeSet } from '@coreui/icons';
import Accordion from "@material-ui/core/Accordion";
import AccordionDetails from "@material-ui/core/AccordionDetails";
import AccordionSummary from "@material-ui/core/AccordionSummary";
import Typography from "@material-ui/core/Typography";
import ExpandMoreIcon from "@material-ui/icons/ExpandMore";

import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import CachedIcon from '@mui/icons-material/Cached';
import PauseIcon from '@mui/icons-material/Pause';
import PauseCircleFilledIcon from '@mui/icons-material/PauseCircleFilled';
import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline';
import ErrorIcon from '@mui/icons-material/Error';

import "react-inputs-validation/lib/react-inputs-validation.min.css";
import {Wave} from "react-animated-text";
import ReactTextTransition from "react-text-transition";
import ModalProblematic from "../modals/modalProblematic.js";
import ModalFunctionnality from "../modals/modalFunctionnality.js";
import ModalTechnology from "../modals/modalTechnology.js";

import Select from "react-select";
import makeAnimated from "react-select/animated";

import Modal from "react-modal";

import {Textarea, Textbox} from "react-inputs-validation";

import Card from "react-bootstrap/Card";

import {
    CAlert,
    CButton,
    CCard,
    CCardBody,
    CCardFooter,
    CCardHeader,
    CCol,
    CContainer,
    CDataTable,
    CRow,
    CTooltip
} from "@coreui/react";

import PhoneInput from "react-phone-input-2";
import "react-phone-input-2/lib/style.css";
import {Link} from "react-router-dom";

import "../../css/style.css";

import { makeStyles } from "@material-ui/core/styles";

import Spinner from "react-bootstrap/Spinner";
import Timeline from '@material-ui/lab/Timeline';
import TimelineItem from '@material-ui/lab/TimelineItem';
import TimelineSeparator from '@material-ui/lab/TimelineSeparator';
import TimelineConnector from '@material-ui/lab/TimelineConnector';
import TimelineContent from '@material-ui/lab/TimelineContent';
import Paper from '@mui/material/Paper';


const useStyles = makeStyles({
    timeline: {
        transform: "rotate(90deg)"
    },
    timelineContentContainer: {
        textAlign: "left"
    },
    timelineContent: {
        display: "inline-block",
        transform: "rotate(-90deg)",
        textAlign: "center",
        minWidth: 50
    },
    timelineIcon: {
        transform: "rotate(-90deg)"
    }
});

function StudentTimeline2() {

    const classes = useStyles();

    return (
        <div className="App">
            sdfvsdfvsdfvsdfv
            <br/>
            <Timeline className={classes.timeline} align="alternate">
                <TimelineItem>
                    <TimelineSeparator>
                        <CheckCircleOutlineIcon
                            color="primary"
                            className={classes.timelineIcon}
                        />
                        <TimelineConnector />
                    </TimelineSeparator>
                    <TimelineContent className={classes.timelineContentContainer}>
                        <Paper className={classes.timelineContent}>
                            <Typography>Eat</Typography>
                        </Paper>
                    </TimelineContent>
                </TimelineItem>
                <TimelineItem>
                    <TimelineSeparator>
                        <PauseCircleFilledIcon
                            color="primary"
                            className={classes.timelineIcon}
                        />
                        <TimelineConnector />
                    </TimelineSeparator>
                    <TimelineContent className={classes.timelineContentContainer}>
                        <Paper className={classes.timelineContent}>
                            <Typography>Code</Typography>
                        </Paper>
                    </TimelineContent>
                </TimelineItem>
                <TimelineItem>
                    <TimelineSeparator>
                        <CachedIcon color="primary" className={classes.timelineIcon} />
                        <TimelineConnector />
                    </TimelineSeparator>
                    <TimelineContent className={classes.timelineContentContainer}>
                        <Paper className={classes.timelineContent}>
                            <Typography>Sleep</Typography>
                        </Paper>
                    </TimelineContent>
                </TimelineItem>
                <TimelineItem>
                    <TimelineSeparator>
                        <CachedIcon color="primary" className={classes.timelineIcon} />
                        <TimelineConnector />
                    </TimelineSeparator>
                    <TimelineContent className={classes.timelineContentContainer}>
                        <Paper className={classes.timelineContent}>
                            <Typography>Sleep</Typography>
                        </Paper>
                    </TimelineContent>
                </TimelineItem>
                <TimelineItem>
                    <TimelineSeparator>
                        <CachedIcon color="primary" className={classes.timelineIcon} />
                        <TimelineConnector />
                    </TimelineSeparator>
                    <TimelineContent className={classes.timelineContentContainer}>
                        <Paper className={classes.timelineContent}>
                            <Typography>Sleep</Typography>
                        </Paper>
                    </TimelineContent>
                </TimelineItem>
                <TimelineItem>
                    <TimelineSeparator>
                        <CachedIcon color="primary" className={classes.timelineIcon} />
                        <TimelineConnector />
                    </TimelineSeparator>
                    <TimelineContent className={classes.timelineContentContainer}>
                        <Paper className={classes.timelineContent}>
                            <Typography>Sleep</Typography>
                        </Paper>
                    </TimelineContent>
                </TimelineItem>
                <TimelineItem>
                    <TimelineSeparator>
                        <CachedIcon color="primary" className={classes.timelineIcon} />
                        <TimelineConnector />
                    </TimelineSeparator>
                    <TimelineContent className={classes.timelineContentContainer}>
                        <Paper className={classes.timelineContent}>
                            <Typography>Sleep</Typography>
                        </Paper>
                    </TimelineContent>
                </TimelineItem>
                <TimelineItem>
                    <TimelineSeparator>
                        <CachedIcon color="primary" className={classes.timelineIcon} />
                        <TimelineConnector />
                    </TimelineSeparator>
                    <TimelineContent className={classes.timelineContentContainer}>
                        <Paper className={classes.timelineContent}>
                            <Typography>Sleep</Typography>
                        </Paper>
                    </TimelineContent>
                </TimelineItem><TimelineItem>
                <TimelineSeparator>
                    <CachedIcon color="primary" className={classes.timelineIcon} />
                    <TimelineConnector />
                </TimelineSeparator>
                <TimelineContent className={classes.timelineContentContainer}>
                    <Paper className={classes.timelineContent}>
                        <Typography>Sleep</Typography>
                    </Paper>
                </TimelineContent>
            </TimelineItem><TimelineItem>
                <TimelineSeparator>
                    <CachedIcon color="primary" className={classes.timelineIcon} />
                    <TimelineConnector />
                </TimelineSeparator>
                <TimelineContent className={classes.timelineContentContainer}>
                    <Paper className={classes.timelineContent}>
                        <Typography>Sleep</Typography>
                    </Paper>
                </TimelineContent>
            </TimelineItem>
                <TimelineItem>
                    <TimelineSeparator>
                        <CachedIcon color="primary" className={classes.timelineIcon} />
                        <TimelineConnector />
                    </TimelineSeparator>
                    <TimelineContent className={classes.timelineContentContainer}>
                            <CButton  shape="pill"
                                      color="dark"
                                      size="sm"
                                      aria-expanded="true">
                                <CTooltip content="Consulter Coordonnées Étudiant" placement="top">
                                    <CIcon content={freeSet.cilPhone} />
                                </CTooltip>
                            </CButton>
                    </TimelineContent>
                </TimelineItem>
                <TimelineItem>
                    <TimelineSeparator>
                        <ErrorIcon color="primary" className={classes.timelineIcon} />
                    </TimelineSeparator>
                    <TimelineContent className={classes.timelineContentContainer}>
                        <Paper className={classes.timelineContent}>
                            <Typography>Sleep</Typography>
                        </Paper>
                    </TimelineContent>
                </TimelineItem>
            </Timeline>
        </div>
    );
}

export default StudentTimeline2