import React from "react";
import { NavLink } from "react-router-dom";

const PortfolioModal = ({ data, closeModal }) => {
    return (
        <>
            Created - <span>{data}</span>
        </>
    );
};

export default PortfolioModal;
