import { Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from '@mui/material';
import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom';
import './Table.css';

function createData(id, name, category, price) {
  return { id, name, category, price };
}


function Tabel(props) {
  const rows = props.books.map((book) => (createData(book.id, book.title, book.category, book.price)))
  const navigate = useNavigate();

  const onClickHandler = ({row}) => {
    navigate(`/book/?id=${row.id}`);
  }

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
            <TableRow className='table-row'
              onClick={()=>onClickHandler({row})}
              key={row.id}
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