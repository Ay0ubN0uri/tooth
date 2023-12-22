import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './student.reducer';

export const StudentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const studentEntity = useAppSelector(state => state.student.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="studentDetailsHeading">
          <Translate contentKey="toothApp.student.detail.title">Student</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{studentEntity.id}</dd>
          <dt>
            <span id="number">
              <Translate contentKey="toothApp.student.number">Number</Translate>
            </span>
          </dt>
          <dd>{studentEntity.number}</dd>
          <dt>
            <span id="cne">
              <Translate contentKey="toothApp.student.cne">Cne</Translate>
            </span>
          </dt>
          <dd>{studentEntity.cne}</dd>
          <dt>
            <span id="cin">
              <Translate contentKey="toothApp.student.cin">Cin</Translate>
            </span>
          </dt>
          <dd>{studentEntity.cin}</dd>
          <dt>
            <span id="birthDay">
              <Translate contentKey="toothApp.student.birthDay">Birth Day</Translate>
            </span>
          </dt>
          <dd>{studentEntity.birthDay ? <TextFormat value={studentEntity.birthDay} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="toothApp.student.user">User</Translate>
          </dt>
          <dd>{studentEntity.user ? studentEntity.user.login : ''}</dd>
          <dt>
            <Translate contentKey="toothApp.student.groupe">Groupe</Translate>
          </dt>
          <dd>
            {studentEntity.groupes
              ? studentEntity.groupes.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.code}</a>
                    {studentEntity.groupes && i === studentEntity.groupes.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/student" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/student/${studentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StudentDetail;
