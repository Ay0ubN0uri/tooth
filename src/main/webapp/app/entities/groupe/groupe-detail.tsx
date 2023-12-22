import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './groupe.reducer';

export const GroupeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const groupeEntity = useAppSelector(state => state.groupe.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="groupeDetailsHeading">
          <Translate contentKey="toothApp.groupe.detail.title">Groupe</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{groupeEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="toothApp.groupe.code">Code</Translate>
            </span>
          </dt>
          <dd>{groupeEntity.code}</dd>
          {/* <dt>
            <span id="year">
              <Translate contentKey="toothApp.groupe.year">Year</Translate>
            </span>
          </dt>
          <dd>{groupeEntity.year}</dd> */}
          <dt>
            <Translate contentKey="toothApp.groupe.professor">Professor</Translate>
          </dt>
          <dd>{groupeEntity.professor ? `${groupeEntity.professor.user.firstName} ${groupeEntity.professor.user.lastName}` : ''}</dd>
          <dt>
            <Translate contentKey="toothApp.groupe.academicYear">Academic Year</Translate>
          </dt>
          <dd>{groupeEntity.academicYear ? groupeEntity.academicYear.year : ''}</dd>
        </dl>
        <Button tag={Link} to="/groupe" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/groupe/${groupeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default GroupeDetail;
