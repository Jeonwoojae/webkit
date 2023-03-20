import React from 'react'
import RecommendSlide from '../components/RecommendSlide'

function Main() {
  return (
    <div>
      <div>
        추천 도서
      </div>
      <RecommendSlide />
      <div>
        검색창 구역
      </div>
      <div>선택한 메뉴를 표시</div>
      <div>
        최근 추가된 책 목록
      </div>
    </div>
  )
}

export default Main