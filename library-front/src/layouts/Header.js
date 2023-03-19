import React from "react";
import "./Header.css";

function Header() {
  return (
    <>
      <div className="header">
        <div>OK23 Comics</div>
        <nav className="header__navi">
          <ul>
            <li>HOME</li>
            <li>로그인</li>
            <li>회원가입</li>
            <li>고객센터</li>
            <li>제휴문의</li>
            <li>회사 소개</li>
          </ul>
        </nav>
      </div>
    </>
  );
}

export default Header;
