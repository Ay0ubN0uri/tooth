import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './academic-year.reducer';

export const AcademicYearDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const academicYearEntity = useAppSelector(state => state.academicYear.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="academicYearDetailsHeading">
          <Translate contentKey="toothApp.academicYear.detail.title">AcademicYear</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{academicYearEntity.id}</dd>
          <dt>
            <span id="year">
              <Translate contentKey="toothApp.academicYear.year">Year</Translate>
            </span>
          </dt>
          <dd>{academicYearEntity.year}</dd>
          <dt>
            <span id="startingDate">
              <Translate contentKey="toothApp.academicYear.startingDate">Starting Date</Translate>
            </span>
          </dt>
          <dd>
            {academicYearEntity.startingDate ? (
              <TextFormat value={academicYearEntity.startingDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endingDate">
              <Translate contentKey="toothApp.academicYear.endingDate">Ending Date</Translate>
            </span>
          </dt>
          <dd>
            {academicYearEntity.endingDate ? (
              <TextFormat value={academicYearEntity.endingDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/academic-year" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/academic-year/${academicYearEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AcademicYearDetail;
