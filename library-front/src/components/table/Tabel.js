import { Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from '@mui/material';
import React from 'react'
import './Table.css';

function createData(name, category, price) {
  return { name, category, price };
}

const rows = [
  createData('이토 준지 걸작집 11 궤담', '공포', 10000),
  createData('신의 지문 1~4(완)', '판타지', 9000),
  createData('슬램덩크 오리지널8', '스포츠', 16000),
  createData('심야식당 26권', "일상", 3710),
];

function Tabel() {
  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 650 }} aria-label="simple table">
        <TableHead className='table-header'>
          <TableRow>
            <TableCell align="center">제목</TableCell>
            <TableCell align="center">카테고리</TableCell>
            <TableCell align="center">가격</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
            <TableRow
              key={row.name}
              sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
            >
              <TableCell component="th" scope="row">
                {row.name}
              </TableCell>
              <TableCell align="right">{row.category}</TableCell>
              <TableCell align="right">{row.price}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  )
}

export default Tabel