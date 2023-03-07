import React, { useState } from 'react';
import "../../css/hi.css";
import { Timeline } from "react-beautiful-horizontal-timeline";

function StudentTimeline() {

    const myList = [
        {
            date: "2018-03-22",
            name: "Event number 01",
            s: "lorem imp ",
            t: "maor k"
        },
        {
            date: "2018-01-18",
            name: "Event number 02",
            s: "lorem imp",
            t: "Maor"
        },
        {
            date: "2021-04-01",
            name: "Event number 03",
            s: "lorem ip ",
            t: "Maor"
        },
        {
            date: "2018-03-22",
            name: "Event number 04",
            s: "lorem impo",
            t: "Maor"
        },
        {
            date: "2018-03-22",
            name: "Event number 05",
            s: "Extreme ",
            t: "Maor tt"
        },
        {
            date: "2018-03-22",
            name: "Event number 06",
            s: "lorem imp ",
            t: "Maor"
        },
        {
            date: "2018-03-22",
            name: "Event number 07",
            s: "lorem ips ",
            t: "Maor"
        },
        {
            date: "2018-03-22",
            name: "Event number 08",
            s: "lorem ips ",
            t: "Maor"
        },
        {
            date: "2018-03-22",
            name: "Event number 09",
            s: "lorem ips ",
            t: "Maor"
        },
        {
            date: "2018-03-22",
            name: "Event number 10",
            s: "lorem imp ",
            t: "Maor"
        },
        {
            name: "Extreme  at Maor Tt10",
            data: "2018-03-22",
            s: "lorem imp ",
            t: "Maor"
        }
    ];

    const [labelWidth, setlabelWidth] = useState(140);
    const [amountMove, setamountMove] = useState(350);
    const [lineColor, setlineColor] = useState("#61dafb");
    const [darkMode, setdarkMode] = useState(false);
    const [eventTextAlignCenter, seteventTextAlignCenter] = useState(true);
    const [showSlider, setshowSlider] = useState(true);
    const [arrowsSize, setarrowsSize] = useState(false);

    return (
        <div className="App">
            timerline 1
            <br/>
            <Timeline
                myList={myList}
                labelWidth={labelWidth}
                amountMove={amountMove}
                lineColor={lineColor}
                darkMode={darkMode}
                eventTextAlignCenter={eventTextAlignCenter}
                showSlider={showSlider}
                arrowsSize={arrowsSize}
                placeholder="hjbvsdjhsd"
            />
        </div>
    );
}

export default StudentTimeline