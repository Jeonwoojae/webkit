import React from "react";
import "./Header.css";

function Header() {
  return (
    <>
      <div className="header">
        <div className="header__logo">OK23 Comics</div>
        <nav>
          <ul className="header__list">
            <li className="header__item">HOME</li>
            <li className="header__item">로그인</li>
            <li className="header__item">회원가입</li>
            <li className="header__item">고객센터</li>
            <li className="header__item">제휴문의</li>
            <li className="header__item">회사 소개</li>
          </ul>
        </nav>
      </div>
    </>
  );
}

export default Header;
