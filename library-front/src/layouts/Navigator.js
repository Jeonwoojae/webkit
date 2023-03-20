import React from "react";
import "./Navigator.css";

function Navigator() {
  return (
    <div className="navi">
      <ul className="navi__list">
        <li className="navi__item">HOME</li>
        <li className="navi__item">국내 만화</li>
        <li className="navi__item">일본 만화</li>
        <li className="navi__item">미국 만화</li>
      </ul>
      <div className="division-line"></div>
    </div>
  );
}

export default Navigator;
