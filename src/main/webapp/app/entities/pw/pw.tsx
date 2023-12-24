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

import { getEntities } from './pw.reducer';

export const PW = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const pWList = useAppSelector(state => state.pW.entities);
  const loading = useAppSelector(state => state.pW.loading);

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
      <h2 id="pw-heading" data-cy="PWHeading">
        <Translate contentKey="toothApp.pW.home.title">Pactice Works</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="toothApp.pW.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/pw/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="toothApp.pW.home.createLabel">Create new PW</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {pWList && pWList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="toothApp.pW.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('title')}>
                  <Translate contentKey="toothApp.pW.title">Title</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('title')} />
                </th>
                <th className="hand" onClick={sort('objectif')}>
                  <Translate contentKey="toothApp.pW.objectif">Objectif</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('objectif')} />
                </th>
                <th className="hand" onClick={sort('docs')}>
                  <Translate contentKey="toothApp.pW.docs">Docs</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('docs')} />
                </th>
                <th className="hand" onClick={sort('deadline')}>
                  <Translate contentKey="toothApp.pW.deadline">Deadline</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('deadline')} />
                </th>
                <th>
                  <Translate contentKey="toothApp.pW.tooth">Tooth</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="toothApp.pW.academicYear">Academic Year</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="toothApp.pW.groupe">Groupe</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {pWList.map((pW, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/pw/${pW.id}`} color="link" size="sm">
                      {pW.id}
                    </Button>
                  </td>
                  <td>{pW.title}</td>
                  <td>{pW.objectif}</td>
                  <td>{pW.docs}</td>
                  <td>{pW.deadline ? <TextFormat type="date" value={pW.deadline} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{pW.tooth ? <Link to={`/tooth/${pW.tooth.id}`}>{pW.tooth.name}</Link> : ''}</td>
                  <td>{pW.academicYear ? <Link to={`/academic-year/${pW.academicYear.id}`}>{pW.academicYear.year}</Link> : ''}</td>
                  <td>
                    {pW.groupes ? pW.groupes.length : 0}
                    {/* {pW.groupes
                      ? pW.groupes.map((val, j) => (
                          <span key={j}>
                            <Link to={`/groupe/${val.id}`}>{val.code}</Link>
                            {j === pW.groupes.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null} */}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/pw/${pW.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/pw/${pW.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/pw/${pW.id}/delete`)}
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
              <Translate contentKey="toothApp.pW.home.notFound">No PWS found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default PW;
