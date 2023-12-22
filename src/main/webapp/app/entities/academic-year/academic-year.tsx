import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './academic-year.reducer';

export const AcademicYear = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const academicYearList = useAppSelector(state => state.academicYear.entities);
  const loading = useAppSelector(state => state.academicYear.loading);

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
      <h2 id="academic-year-heading" data-cy="AcademicYearHeading">
        <Translate contentKey="toothApp.academicYear.home.title">Academic Years</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="toothApp.academicYear.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/academic-year/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="toothApp.academicYear.home.createLabel">Create new Academic Year</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {academicYearList && academicYearList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="toothApp.academicYear.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('year')}>
                  <Translate contentKey="toothApp.academicYear.year">Year</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('year')} />
                </th>
                <th className="hand" onClick={sort('startingDate')}>
                  <Translate contentKey="toothApp.academicYear.startingDate">Starting Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('startingDate')} />
                </th>
                <th className="hand" onClick={sort('endingDate')}>
                  <Translate contentKey="toothApp.academicYear.endingDate">Ending Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('endingDate')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {academicYearList.map((academicYear, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/academic-year/${academicYear.id}`} color="link" size="sm">
                      {academicYear.id}
                    </Button>
                  </td>
                  <td>{academicYear.year}</td>
                  <td>
                    {academicYear.startingDate ? (
                      <TextFormat type="date" value={academicYear.startingDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {academicYear.endingDate ? <TextFormat type="date" value={academicYear.endingDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/academic-year/${academicYear.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/academic-year/${academicYear.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/academic-year/${academicYear.id}/delete`)}
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
              <Translate contentKey="toothApp.academicYear.home.notFound">No Academic Years found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default AcademicYear;
