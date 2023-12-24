import { useAppDispatch, useAppSelector } from 'app/config/store';
import React, { useEffect, useState } from 'react';
import { Translate, getSortState } from 'react-jhipster';
import { Button, CardTitle, Col, Input, Label, Row } from 'reactstrap';
import { getEntities as getAcademicYearEntities } from '../academic-year/academic-year.reducer';
import { useNavigate } from 'react-router';
import { getEntities as getGroupEntities } from '../groupe/groupe.reducer';

const MyStudents = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const [academicYear, setAcademicYear] = useState<string>('');
  const [group, setGroup] = useState<string>('');
  const [filteredGroupList, setFilteredGroupList] = useState([]);
  const academicYearList = useAppSelector(state => state.academicYear.entities);
  const groupeList = useAppSelector(state => state.groupe.entities);

  const getAllEntities = () => {
    dispatch(
      getAcademicYearEntities({
        sort: ``,
      }),
    );
    dispatch(
      getGroupEntities({
        sort: ``,
      }),
    );
  };
  useEffect(() => {
    getAllEntities();
  }, []);
  return (
    <div className="container mt-5">
      <Row>
        <Col xs={6}>
          <h1>
            <Translate contentKey="toothApp.myStudents.home.title">My Students</Translate>
          </h1>
        </Col>
        <Col xs={6} className="text-right d-flex flex-row justify-content-center align-items-center">
          <Button
            color="info"
            onClick={() => {
              setAcademicYear('');
              setGroup('');
            }}
          >
            Reset
          </Button>
        </Col>
      </Row>
      <Row className="mt-2">
        <Col>
          <div className="mb-3">
            <Label className="h4">Select an academic year:</Label>
            <Input
              value={academicYear}
              onChange={event => {
                setAcademicYear(event.target.value);
                setFilteredGroupList(groupeList.filter(grp => grp.academicYear.id === parseInt(event.target.value, 10)));
              }}
              className="w-50"
              id="exampleSelect"
              name="select"
              type="select"
            >
              {academicYearList &&
                academicYearList.map((aYear, i) => (
                  <option value={aYear.id} key={aYear.id}>
                    {aYear.year}
                  </option>
                ))}
            </Input>
          </div>
        </Col>
        <Col>
          <div className="mb-3">
            <Label className="h4">Select a groupe:</Label>
            <Input
              className="w-50"
              id="exampleSelect"
              name="select"
              type="select"
              disabled={academicYear === ''}
              value={group}
              onChange={event => {
                setGroup(event.target.value);
              }}
            >
              {filteredGroupList &&
                filteredGroupList.map((grp, i) => (
                  <option value={grp.id} key={grp.id}>
                    {grp.code}
                  </option>
                ))}
            </Input>
          </div>
        </Col>
      </Row>
      <Row className="float-left">
        <Button
          color="primary"
          disabled={academicYear === '' || group === ''}
          // onClick={() => {
          //   console.log(academicYear, group);
          // }}
        >
          Fetch
        </Button>
      </Row>
      <div className="row">
        <div className="col-xl-3 col-md-6">
          <div className="card card-stats">
            <div className="card-body">
              <div className="row">
                <div className="col">
                  <h5 className="card-title text-uppercase text-muted mb-0">Total traffic</h5>
                  <span className="h2 font-weight-bold mb-0">350,897</span>
                </div>
                <div className="col-auto">
                  <div className="icon icon-shape bg-gradient-red text-white rounded-circle shadow">
                    <i className="ni ni-active-40"></i>
                  </div>
                </div>
              </div>
              <p className="mt-3 mb-0 text-sm">
                <span className="text-success mr-2">
                  <i className="fa fa-arrow-up"></i> 3.48%
                </span>
                <span className="text-nowrap">Since last month</span>
              </p>
            </div>
          </div>
        </div>
        <div className="col-xl-3 col-md-6">
          <div className="card card-stats">
            <div className="card-body">
              <div className="row">
                <div className="col">
                  <h5 className="card-title text-uppercase text-muted mb-0">New users</h5>
                  <span className="h2 font-weight-bold mb-0">2,356</span>
                </div>
                <div className="col-auto">
                  <div className="icon icon-shape bg-gradient-orange text-white rounded-circle shadow">
                    <i className="ni ni-chart-pie-35"></i>
                  </div>
                </div>
              </div>
              <p className="mt-3 mb-0 text-sm">
                <span className="text-success mr-2">
                  <i className="fa fa-arrow-up"></i> 3.48%
                </span>
                <span className="text-nowrap">Since last month</span>
              </p>
            </div>
          </div>
        </div>
        <div className="col-xl-3 col-md-6">
          <div className="card card-stats">
            <div className="card-body">
              <div className="row">
                <div className="col">
                  <h5 className="card-title text-uppercase text-muted mb-0">Sales</h5>
                  <span className="h2 font-weight-bold mb-0">924</span>
                </div>
                <div className="col-auto">
                  <div className="icon icon-shape bg-gradient-green text-white rounded-circle shadow">
                    <i className="ni ni-money-coins"></i>
                  </div>
                </div>
              </div>
              <p className="mt-3 mb-0 text-sm">
                <span className="text-success mr-2">
                  <i className="fa fa-arrow-up"></i> 3.48%
                </span>
                <span className="text-nowrap">Since last month</span>
              </p>
            </div>
          </div>
        </div>
        <div className="col-xl-3 col-md-6">
          <div className="card card-stats">
            <div className="card-body">
              <div className="row">
                <div className="col">
                  <h5 className="card-title text-uppercase text-muted mb-0">Performance</h5>
                  <span className="h2 font-weight-bold mb-0">49,65%</span>
                </div>
                <div className="col-auto">
                  <div className="icon icon-shape bg-gradient-info text-white rounded-circle shadow">
                    <i className="ni ni-chart-bar-32"></i>
                  </div>
                </div>
              </div>
              <p className="mt-3 mb-0 text-sm">
                <span className="text-success mr-2">
                  <i className="fa fa-arrow-up"></i> 3.48%
                </span>
                <span className="text-nowrap">Since last month</span>
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MyStudents;
