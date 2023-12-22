import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './student-pw.reducer';

export const StudentPWDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const studentPWEntity = useAppSelector(state => state.studentPW.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="studentPWDetailsHeading">
          <Translate contentKey="toothApp.studentPW.detail.title">StudentPW</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{studentPWEntity.id}</dd>
          <dt>
            <span id="time">
              <Translate contentKey="toothApp.studentPW.time">Time</Translate>
            </span>
          </dt>
          <dd>{studentPWEntity.time}</dd>
          <dt>
            <span id="imageFront">
              <Translate contentKey="toothApp.studentPW.imageFront">Image Front</Translate>
            </span>
          </dt>
          <dd>
            {studentPWEntity.imageFront ? (
              <div>
                {studentPWEntity.imageFrontContentType ? (
                  <a onClick={openFile(studentPWEntity.imageFrontContentType, studentPWEntity.imageFront)}>
                    <img
                      src={`data:${studentPWEntity.imageFrontContentType};base64,${studentPWEntity.imageFront}`}
                      style={{ maxHeight: '120px' }}
                    />
                  </a>
                ) : null}
                {/* <span>
                  {studentPWEntity.imageFrontContentType}, {byteSize(studentPWEntity.imageFront)}
                </span> */}
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="imageSide">
              <Translate contentKey="toothApp.studentPW.imageSide">Image Side</Translate>
            </span>
          </dt>
          <dd>
            {studentPWEntity.imageSide ? (
              <div>
                {studentPWEntity.imageSideContentType ? (
                  <a onClick={openFile(studentPWEntity.imageSideContentType, studentPWEntity.imageSide)}>
                    <img
                      src={`data:${studentPWEntity.imageSideContentType};base64,${studentPWEntity.imageSide}`}
                      style={{ maxHeight: '120px' }}
                    />
                  </a>
                ) : null}
                {/* <span>
                  {studentPWEntity.imageSideContentType}, {byteSize(studentPWEntity.imageSide)}
                </span> */}
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="date">
              <Translate contentKey="toothApp.studentPW.date">Date</Translate>
            </span>
          </dt>
          <dd>{studentPWEntity.date ? <TextFormat value={studentPWEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="toothApp.studentPW.student">Student</Translate>
          </dt>
          <dd>{studentPWEntity.student ? `${studentPWEntity.student.user.firstName} ${studentPWEntity.student.user.lastName}` : ''}</dd>
          <dt>
            <Translate contentKey="toothApp.studentPW.pw">Pw</Translate>
          </dt>
          <dd>{studentPWEntity.pw ? studentPWEntity.pw.title : ''}</dd>
          <dt>
            <Translate contentKey="toothApp.studentPW.academicYear">Academic Year</Translate>
          </dt>
          <dd>{studentPWEntity.academicYear ? studentPWEntity.academicYear.year : ''}</dd>
        </dl>
        <Button tag={Link} to="/student-pw" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/student-pw/${studentPWEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StudentPWDetail;
