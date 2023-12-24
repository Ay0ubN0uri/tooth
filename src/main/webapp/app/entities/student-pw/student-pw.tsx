import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './student-pw.reducer';

export const StudentPW = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const studentPWList = useAppSelector(state => state.studentPW.entities);
  const loading = useAppSelector(state => state.studentPW.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="student-pw-heading" data-cy="StudentPWHeading">
        <Translate contentKey="toothApp.studentPW.home.title">Student Practice Works</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="toothApp.studentPW.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/student-pw/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="toothApp.studentPW.home.createLabel">Create new Student PW</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {studentPWList && studentPWList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="toothApp.studentPW.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('time')}>
                  <Translate contentKey="toothApp.studentPW.time">Time</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('time')} />
                </th>
                <th className="hand" onClick={sort('imageFront')}>
                  <Translate contentKey="toothApp.studentPW.imageFront">Image Front</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('imageFront')} />
                </th>
                <th className="hand" onClick={sort('imageSide')}>
                  <Translate contentKey="toothApp.studentPW.imageSide">Image Side</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('imageSide')} />
                </th>
                <th className="hand" onClick={sort('date')}>
                  <Translate contentKey="toothApp.studentPW.date">Date</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('date')} />
                </th>
                <th>
                  <Translate contentKey="toothApp.studentPW.student">Student</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="toothApp.studentPW.pw">Pw</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="toothApp.studentPW.academicYear">Academic Year</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {studentPWList.map((studentPW, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/student-pw/${studentPW.id}`} color="link" size="sm">
                      {studentPW.id}
                    </Button>
                  </td>
                  <td>{studentPW.time}</td>
                  <td>
                    {studentPW.imageFront ? (
                      <div>
                        {studentPW.imageFrontContentType ? (
                          <a onClick={openFile(studentPW.imageFrontContentType, studentPW.imageFront)}>
                            <img
                              src={`data:${studentPW.imageFrontContentType};base64,${studentPW.imageFront}`}
                              style={{ maxHeight: '60px' }}
                            />
                            &nbsp;
                          </a>
                        ) : null}
                        {/* <span>
                          {studentPW.imageFrontContentType}, {byteSize(studentPW.imageFront)}
                        </span> */}
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {studentPW.imageSide ? (
                      <div>
                        {studentPW.imageSideContentType ? (
                          <a onClick={openFile(studentPW.imageSideContentType, studentPW.imageSide)}>
                            <img
                              src={`data:${studentPW.imageSideContentType};base64,${studentPW.imageSide}`}
                              style={{ maxHeight: '60px' }}
                            />
                            &nbsp;
                          </a>
                        ) : null}
                        {/* <span>
                          {studentPW.imageSideContentType}, {byteSize(studentPW.imageSide)}
                        </span> */}
                      </div>
                    ) : null}
                  </td>
                  <td>{studentPW.date ? <TextFormat type="date" value={studentPW.date} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    {studentPW.student ? <Link to={`/student/${studentPW.student.id}`}>{studentPW.student.user.firstName}</Link> : ''}
                  </td>
                  <td>{studentPW.pw ? <Link to={`/pw/${studentPW.pw.id}`}>{studentPW.pw.title}</Link> : ''}</td>
                  <td>
                    {studentPW.academicYear ? (
                      <Link to={`/academic-year/${studentPW.academicYear.id}`}>{studentPW.academicYear.year}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/student-pw/${studentPW.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/student-pw/${studentPW.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/student-pw/${studentPW.id}/delete`)}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="toothApp.studentPW.home.notFound">No Student PWS found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default StudentPW;
