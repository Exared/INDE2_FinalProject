import * as React from 'react';
import Link from '@mui/material/Link';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Typography from '@mui/material/Typography';
import { useEffect, useState } from 'react';
import axios from 'axios';


// Generate Order Data
function createData(id, date, drone_id,longitude,latitude,name) {
  return { id, date, drone_id, longitude, latitude, name};
}


function preventDefault() {
  window.location.reload();
}

export default function Orders() {
  const [rowsa, setRowsa] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get('http://localhost:4000/alert');
        setRowsa(response.data);
      } catch (error) {
        console.error(error);
      }
    };
    fetchData();
  }, []);
  console.log(rowsa[0])
  return (
    <React.Fragment>
      <Typography variant="h1" component="div" sx={{ my: 5 ,textAlign:'center'}}>
        All Alerts
      </Typography>
      <Link color="primary" href="/alerts" onClick={preventDefault} sx={{ mt: 3 }}>
        Refresh Data
      </Link>
      <Table size="Huge">
        <TableHead>
          <TableRow>
            <TableCell>
            <Typography variant="h6" component="div">
            Drone ID
            </Typography>
            </TableCell>
            <TableCell>
            <Typography variant="h6" component="div">
            Longitude
            </Typography>
            </TableCell>
            <TableCell>
            <Typography variant="h6" component="div">
            Latitude
            </Typography>
            </TableCell>
            <TableCell>
            <Typography variant="h6" component="div">
            Name
            </Typography>
            </TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rowsa.map((row) => (
            <TableRow>
              <TableCell>{row.drone_id}</TableCell>
              <TableCell>{row.drone_location.longitude}</TableCell>
              <TableCell>{row.drone_location.latitude}</TableCell>
              <TableCell>{row.person_name}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </React.Fragment>
  );
}
