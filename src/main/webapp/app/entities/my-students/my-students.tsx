import { useAppDispatch, useAppSelector } from 'app/config/store';
import React, { useEffect, useState } from 'react';
import { Translate, getSortState } from 'react-jhipster';
import { Button, CardTitle, Col, Input, Label, Row } from 'reactstrap';
import { getEntities as getAcademicYearEntities } from '../academic-year/academic-year.reducer';
import { useNavigate } from 'react-router';
import { getEntities as getGroupEntities } from '../groupe/groupe.reducer';
import LoadingSpinner from 'app/shared/components/LoadingSpinner';
import { getMyStudents } from './my-students.reducer';
import StudentTable from './student-table';

const MyStudents = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const [academicYear, setAcademicYear] = useState<string>('');
  const [group, setGroup] = useState<string>('');
  const [filteredGroupList, setFilteredGroupList] = useState([]);
  const academicYearList = useAppSelector(state => state.academicYear.entities);
  const groupeList = useAppSelector(state => state.groupe.entities);
  const loading = useAppSelector(state => state.mystudents.loading);
  const students = useAppSelector(state => state.mystudents.students);
  const [isLoading, setIsLoading] = useState(false);
  const [completed, setCompleted] = useState(false);

  const handleFetch = () => {
    // console.log(academicYear, group);
    // console.log(loading);
    setIsLoading(true);
    setCompleted(false);
    dispatch(
      getMyStudents({
        academicYear,
        group,
      }),
    );
  };

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

  useEffect(() => {
    if (!loading) {
      setIsLoading(false);
      setCompleted(true);
    }
  }, [loading]);

  return (
    <div className="container mt-5">
      <Row>
        <Col xs={6}>
          <h1>
            <Translate contentKey="toothApp.myStudents.home.title">My Students</Translate>
          </h1>
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
                const grps = groupeList.filter(grp => grp.academicYear.id === parseInt(event.target.value, 10));
                setFilteredGroupList(grps);
                if (grps.length > 0) {
                  setGroup(grps[0].id);
                } else {
                  setGroup('');
                }
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
      <div className="mb-3">
        <Button
          color="info"
          onClick={() => {
            setAcademicYear('');
            setGroup('');
          }}
        >
          Reset
        </Button>
        <Button color="primary" disabled={academicYear === '' || group === ''} onClick={handleFetch}>
          Fetch
        </Button>
      </div>
      {isLoading && <LoadingSpinner />}
      {completed && <StudentTable students={students} />}
    </div>
  );
};

export default MyStudents;
