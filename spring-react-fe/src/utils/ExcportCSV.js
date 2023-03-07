import React from "react";
import * as FileSaver from "file-saver";
import * as XLSX from "xlsx";
import { CButton } from "@coreui/react";
import CIcon from "@coreui/icons-react";

export const ExportCSV = ({ csvData, fileName }) => {
  const fileType =
    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8";
  const fileExtension = ".xlsx";

  const exportToCSV = (csvData, fileName) => {
    const ws = XLSX.utils.json_to_sheet(csvData);
    const wb = { Sheets: { data: ws }, SheetNames: ["data"] };
    const excelBuffer = XLSX.write(wb, { bookType: "xlsx", type: "array" });
    const data = new Blob([excelBuffer], { type: fileType });
    FileSaver.saveAs(data, fileName + fileExtension);
  };

  return (
    <>
      <CButton
        className="float-right"
        data-tut="reactour__2"
        variant="outline"
        color="danger"
        size="sm"
        onClick={(e) => exportToCSV(csvData, fileName)}
      >
        <CIcon name="cil-lightbulb" /> EXPORTER la Liste en fichier XLSX
      </CButton>
      <br></br>
      <br></br>{" "}
    </>
  );
};
