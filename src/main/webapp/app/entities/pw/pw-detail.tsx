import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './pw.reducer';

export const PWDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const pWEntity = useAppSelector(state => state.pW.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pWDetailsHeading">
          <Translate contentKey="toothApp.pW.detail.title">PW</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{pWEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="toothApp.pW.title">Title</Translate>
            </span>
          </dt>
          <dd>{pWEntity.title}</dd>
          <dt>
            <span id="objectif">
              <Translate contentKey="toothApp.pW.objectif">Objectif</Translate>
            </span>
          </dt>
          <dd>{pWEntity.objectif}</dd>
          <dt>
            <span id="docs">
              <Translate contentKey="toothApp.pW.docs">Docs</Translate>
            </span>
          </dt>
          <dd>{pWEntity.docs}</dd>
          <dt>
            <span id="deadline">
              <Translate contentKey="toothApp.pW.deadline">Deadline</Translate>
            </span>
          </dt>
          <dd>{pWEntity.deadline ? <TextFormat value={pWEntity.deadline} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="toothApp.pW.tooth">Tooth</Translate>
          </dt>
          <dd>{pWEntity.tooth ? pWEntity.tooth.name : ''}</dd>
          <dt>
            <Translate contentKey="toothApp.pW.academicYear">Academic Year</Translate>
          </dt>
          <dd>{pWEntity.academicYear ? pWEntity.academicYear.year : ''}</dd>
          <dt>
            <Translate contentKey="toothApp.pW.groupe">Groupe</Translate>
          </dt>
          <dd>
            {pWEntity.groupes
              ? pWEntity.groupes.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.code}</a>
                    {pWEntity.groupes && i === pWEntity.groupes.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/pw" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pw/${pWEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PWDetail;
