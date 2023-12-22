import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './professor.reducer';

export const ProfessorDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const professorEntity = useAppSelector(state => state.professor.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="professorDetailsHeading">
          <Translate contentKey="toothApp.professor.detail.title">Professor</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{professorEntity.id}</dd>
          <dt>
            <span id="grade">
              <Translate contentKey="toothApp.professor.grade">Grade</Translate>
            </span>
          </dt>
          <dd>{professorEntity.grade}</dd>
          <dt>
            <Translate contentKey="toothApp.professor.user">User</Translate>
          </dt>
          <dd>{professorEntity.user ? professorEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/professor" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/professor/${professorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProfessorDetail;
