import React from 'react'
import { CChartLine } from '@coreui/react-chartjs'
import { getStyle, hexToRgba } from '@coreui/utils/src'
import { useDispatch, useSelector } from "react-redux";
import moment from 'moment';
import { selectSoutenanceStat } from "../../../redux/slices/DepotSlice";


const brandSuccess = getStyle('success') || '#4dbd74'
const brandInfo = getStyle('primary') || '#0000ff'
const brandDanger = getStyle('danger') || '#f86c6b'

const MainChartExample = attributes => {
  const random = (min, max)=>{
    return Math.floor(Math.random() * (max - min + 1) + min)
  }

  const dispatch = useDispatch();
  const [soutenanceStat, errds] = useSelector(selectSoutenanceStat);  // HooksMe 1

  const defaultDatasets = (()=>{
    const data1 = []
    data1.push(Object.values(soutenanceStat[0]))
  
    return [
      {
        label: 'Soutenance(s) Effectuée(s)',
        backgroundColor: hexToRgba(brandInfo, 10),
        borderColor: brandInfo,
        pointHoverBackgroundColor: brandInfo,
        borderWidth: 2,
        data: data1[0]
      }
    ]
  })()

  const days = (()=>{
    const days = [];
    const formattedDays = [];
    days.push(Object.keys(soutenanceStat[0]));

    // moment(startDate).format("YYYY/MM/DD");
    for(let d of days[0])
    {
        formattedDays.push(moment(d).format("DD-MM-YYYY"));
    }
    return formattedDays;
  })()

  const defaultOptions = (()=>{
    return {
        maintainAspectRatio: false,
        legend: {
          display: false
        },
        scales: {
          xAxes: [{
            gridLines: {
              drawOnChartArea: false
            }
          }],
          yAxes: [{
            ticks: {
              beginAtZero: true,
              maxTicksLimit: 1,
              stepSize: Math.ceil(25 / 2),
              max: 25
            },
            gridLines: {
              display: true
            }
          }]
        },
        elements: {
          point: {
            radius: 0,
            hitRadius: 10,
            hoverRadius: 4,
            hoverBorderWidth: 3
          }
        }
      }
    }
  )()

  // render
  return (
    <CChartLine
      {...attributes}
      datasets={defaultDatasets}
      options={defaultOptions}
      labels={days}
    />
  )
}


export default MainChartExample
