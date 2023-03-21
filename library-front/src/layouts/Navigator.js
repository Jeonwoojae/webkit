import React, { useState } from "react";
import MenuItems from "../components/navi/MenuItems";
import { menuItems } from "../value/menuItems";
import "./Navigator.css";

function Navigator() {
  return (
    <>
    <nav className="navi">
      <ul className="menus">
        {menuItems.map((menu, index) => {
          const depthLevel = 0;
          return (
            <MenuItems
              items={menu}
              key={index}
              depthLevel={depthLevel}
            />
          );
        })}
      </ul>
      <div className="nav-division-line"></div>
    </nav>

    </>
  );
}

export default Navigator;
