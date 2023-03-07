import React, { useState, useRef } from "react";
import IdleTimer from "react-idle-timer";
import SessionTimeoutDialog from "./SessionTimeoutDialog";
import moment from "moment";

let countdownInterval;
let timeout = 450000;  // 30000 : wait for 60s

const SessionTimeout = () => {
    const idleTimer = useRef(null);

    const login = {
        logged: true
    };

    const [timeoutModalOpen, setTimeoutModalOpen] = useState(false);
    const [timeoutCountdown, setTimeoutCountdown] = useState(0);

    const onActive = () => {
        // console.log('................0711 onActive : ' , new Date());
        // timer reset automatically.
        if (!timeoutModalOpen) {
            clearSessionInterval();
            clearSessionTimeout();
        }
    };

    const onIdle = () => {
        console.log('--SARS---------------0711 onIdle : ' , moment(new Date()).format("hh:mm:ss"));
        if (login.logged && !timeoutModalOpen) {
            // timeout = setTimeout(() => {
            setTimeout(() => {
                let countDown = 60; //60
                setTimeoutModalOpen(true);
                setTimeoutCountdown(countDown);
                countdownInterval = setInterval(() => {
                    if (countDown > 0) {
                        setTimeoutCountdown(--countDown);
                    } else {
                        handleLogout();
                    }
                }, 1000);
            }, timeout);
        }
    };

    const clearSessionTimeout = () => {
        // console.log('................0711 clearSessionTimeout : ' , new Date());
        clearTimeout(timeout);
    };

    const clearSessionInterval = () => {
        // console.log('................0711 clearSessionInterval : ' , new Date());
        clearInterval(countdownInterval);
    };

    const handleLogout = () => {
    	console.log('................0711 OUT : ' , new Date());
        setTimeoutModalOpen(false);
        clearSessionInterval();
        clearSessionTimeout();
        // alert("Logged Out");
        localStorage.clear();
        sessionStorage.clear();
        window.location.href = "/";
    };

    const handleContinue = () => {
        // console.log('---------------------0711 onIdle : ' , new Date());
        setTimeoutModalOpen(false);
        clearSessionInterval();
        clearSessionTimeout();
        // console.log('---------------------0711 Session BEFORE : ' , new Date());
        /*setTimeout(() => {
        }, 120000);*/
        // console.log('---------------------0711 Session AFTER : ' , new Date());
    };

    return (
        <>
            <IdleTimer
                ref={idleTimer}
                onActive={onActive}
                onIdle={onIdle}
                //timeout={1}
                //debounce={1} // *refuse in MS
                timeout={450000}  // sleep in MS  ** {1000 * 60 * 1} = 1 minute
            />

            <SessionTimeoutDialog
                countdown={timeoutCountdown}
                onContinue={handleContinue}
                onLogout={handleLogout}
                open={timeoutModalOpen}
            />
        </>
    );
};

export default SessionTimeout;
