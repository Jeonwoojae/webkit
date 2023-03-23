import React, { useCallback, useRef } from "react";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import "./RecommendSlide.css";

function RecommendSlide() {
  //라이브러리 이벤트 속성과 디자인 요소 스타일링
  const settings = {
    dots: false,
    arrow: true,
    infinite: true,
    speed: 500,
    slidesToShow: 5,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 2000,
    pauseOnHover: true,
  };

  const slickRef = useRef(null);

  const previous = useCallback(() => slickRef.current.slickPrev(), []);
  const next = useCallback(() => slickRef.current.slickNext(), []);

  return (
    <div>
      <Slider {...settings} ref={slickRef}>
        <img className="banner-items" src={`${process.env.PUBLIC_URL}/assets/1.jpg`} />
        <img className="banner-items" src={`${process.env.PUBLIC_URL}/assets/2.jpg`} />
        <img className="banner-items" src={`${process.env.PUBLIC_URL}/assets/3.jpg`} />
        <img className="banner-items" src={`${process.env.PUBLIC_URL}/assets/4.jpg`} />
        <img className="banner-items" src={`${process.env.PUBLIC_URL}/assets/5.jpg`} />
        <img className="banner-items" src={`${process.env.PUBLIC_URL}/assets/6.jpg`} />
        <img className="banner-items" src={`${process.env.PUBLIC_URL}/assets/7.jpg`} />
        <img className="banner-items" src={`${process.env.PUBLIC_URL}/assets/8.jpg`} />
        <img className="banner-items" src={`${process.env.PUBLIC_URL}/assets/9.jpg`} />
        <img className="banner-items" src={`${process.env.PUBLIC_URL}/assets/10.jpg`} />
      </Slider>

      {/* <div>
        <div className="preButton" onClick={previous}>
          <img src={`${process.env.PUBLIC_URL}/assets/icon/previous.png`} alt={"pre-arrow"} />
        </div>
        <div className="nextButton" onClick={next}>
          <img src={`${process.env.PUBLIC_URL}/assets/icon/next.png`} alt={"next-arrow"} />
        </div>
      </div> */}
    </div>
  );
}

export default RecommendSlide;
