import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStudent } from 'app/shared/model/student.model';
import { getEntities as getStudents } from 'app/entities/student/student.reducer';
import { IPW } from 'app/shared/model/pw.model';
import { getEntities as getPWs } from 'app/entities/pw/pw.reducer';
import { IAcademicYear } from 'app/shared/model/academic-year.model';
import { getEntities as getAcademicYears } from 'app/entities/academic-year/academic-year.reducer';
import { IStudentPW } from 'app/shared/model/student-pw.model';
import { getEntity, updateEntity, createEntity, reset } from './student-pw.reducer';

export const StudentPWUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const students = useAppSelector(state => state.student.entities);
  const pWS = useAppSelector(state => state.pW.entities);
  const academicYears = useAppSelector(state => state.academicYear.entities);
  const studentPWEntity = useAppSelector(state => state.studentPW.entity);
  const loading = useAppSelector(state => state.studentPW.loading);
  const updating = useAppSelector(state => state.studentPW.updating);
  const updateSuccess = useAppSelector(state => state.studentPW.updateSuccess);

  const handleClose = () => {
    navigate('/student-pw');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getStudents({}));
    dispatch(getPWs({}));
    dispatch(getAcademicYears({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    values.date = convertDateTimeToServer(values.date);
    if (values.note !== undefined && typeof values.note !== 'number') {
      values.note = Number(values.note);
    }
    if (values.a1ImageSide !== undefined && typeof values.a1ImageSide !== 'number') {
      values.a1ImageSide = Number(values.a1ImageSide);
    }
    if (values.a2ImageSide !== undefined && typeof values.a2ImageSide !== 'number') {
      values.a2ImageSide = Number(values.a2ImageSide);
    }
    if (values.a3ImageSide !== undefined && typeof values.a3ImageSide !== 'number') {
      values.a3ImageSide = Number(values.a3ImageSide);
    }
    if (values.p1ImageSide !== undefined && typeof values.p1ImageSide !== 'number') {
      values.p1ImageSide = Number(values.p1ImageSide);
    }
    if (values.p2ImageSide !== undefined && typeof values.p2ImageSide !== 'number') {
      values.p2ImageSide = Number(values.p2ImageSide);
    }
    if (values.p3ImageSide !== undefined && typeof values.p3ImageSide !== 'number') {
      values.p3ImageSide = Number(values.p3ImageSide);
    }
    if (values.a1ImageFront !== undefined && typeof values.a1ImageFront !== 'number') {
      values.a1ImageFront = Number(values.a1ImageFront);
    }
    if (values.a2ImageFront !== undefined && typeof values.a2ImageFront !== 'number') {
      values.a2ImageFront = Number(values.a2ImageFront);
    }
    if (values.a3ImageFront !== undefined && typeof values.a3ImageFront !== 'number') {
      values.a3ImageFront = Number(values.a3ImageFront);
    }
    if (values.p1ImageFront !== undefined && typeof values.p1ImageFront !== 'number') {
      values.p1ImageFront = Number(values.p1ImageFront);
    }
    if (values.p2ImageFront !== undefined && typeof values.p2ImageFront !== 'number') {
      values.p2ImageFront = Number(values.p2ImageFront);
    }
    if (values.p3ImageFront !== undefined && typeof values.p3ImageFront !== 'number') {
      values.p3ImageFront = Number(values.p3ImageFront);
    }

    const entity = {
      ...studentPWEntity,
      ...values,
      student: students.find(it => it.id.toString() === values.student.toString()),
      pw: pWS.find(it => it.id.toString() === values.pw.toString()),
      academicYear: academicYears.find(it => it.id.toString() === values.academicYear.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          date: displayDefaultDateTime(),
        }
      : {
          ...studentPWEntity,
          date: convertDateTimeFromServer(studentPWEntity.date),
          student: studentPWEntity?.student?.id,
          pw: studentPWEntity?.pw?.id,
          academicYear: studentPWEntity?.academicYear?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="toothApp.studentPW.home.createOrEditLabel" data-cy="StudentPWCreateUpdateHeading">
            <Translate contentKey="toothApp.studentPW.home.createOrEditLabel">Create or edit a StudentPW</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="student-pw-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('toothApp.studentPW.time')} id="student-pw-time" name="time" data-cy="time" type="text" />
              <ValidatedBlobField
                label={translate('toothApp.studentPW.imageFront')}
                id="student-pw-imageFront"
                name="imageFront"
                data-cy="imageFront"
                isImage
                accept="image/*"
              />
              <ValidatedBlobField
                label={translate('toothApp.studentPW.imageSide')}
                id="student-pw-imageSide"
                name="imageSide"
                data-cy="imageSide"
                isImage
                accept="image/*"
              />
              <ValidatedField
                label={translate('toothApp.studentPW.date')}
                id="student-pw-date"
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label={translate('toothApp.studentPW.note')} id="student-pw-note" name="note" data-cy="note" type="text" />
              <ValidatedField
                label={translate('toothApp.studentPW.a1ImageSide')}
                id="student-pw-a1ImageSide"
                name="a1ImageSide"
                data-cy="a1ImageSide"
                type="text"
              />
              <ValidatedField
                label={translate('toothApp.studentPW.a2ImageSide')}
                id="student-pw-a2ImageSide"
                name="a2ImageSide"
                data-cy="a2ImageSide"
                type="text"
              />
              <ValidatedField
                label={translate('toothApp.studentPW.a3ImageSide')}
                id="student-pw-a3ImageSide"
                name="a3ImageSide"
                data-cy="a3ImageSide"
                type="text"
              />
              <ValidatedField
                label={translate('toothApp.studentPW.p1ImageSide')}
                id="student-pw-p1ImageSide"
                name="p1ImageSide"
                data-cy="p1ImageSide"
                type="text"
              />
              <ValidatedField
                label={translate('toothApp.studentPW.p2ImageSide')}
                id="student-pw-p2ImageSide"
                name="p2ImageSide"
                data-cy="p2ImageSide"
                type="text"
              />
              <ValidatedField
                label={translate('toothApp.studentPW.p3ImageSide')}
                id="student-pw-p3ImageSide"
                name="p3ImageSide"
                data-cy="p3ImageSide"
                type="text"
              />
              <ValidatedField
                label={translate('toothApp.studentPW.a1ImageFront')}
                id="student-pw-a1ImageFront"
                name="a1ImageFront"
                data-cy="a1ImageFront"
                type="text"
              />
              <ValidatedField
                label={translate('toothApp.studentPW.a2ImageFront')}
                id="student-pw-a2ImageFront"
                name="a2ImageFront"
                data-cy="a2ImageFront"
                type="text"
              />
              <ValidatedField
                label={translate('toothApp.studentPW.a3ImageFront')}
                id="student-pw-a3ImageFront"
                name="a3ImageFront"
                data-cy="a3ImageFront"
                type="text"
              />
              <ValidatedField
                label={translate('toothApp.studentPW.p1ImageFront')}
                id="student-pw-p1ImageFront"
                name="p1ImageFront"
                data-cy="p1ImageFront"
                type="text"
              />
              <ValidatedField
                label={translate('toothApp.studentPW.p2ImageFront')}
                id="student-pw-p2ImageFront"
                name="p2ImageFront"
                data-cy="p2ImageFront"
                type="text"
              />
              <ValidatedField
                label={translate('toothApp.studentPW.p3ImageFront')}
                id="student-pw-p3ImageFront"
                name="p3ImageFront"
                data-cy="p3ImageFront"
                type="text"
              />
              <ValidatedField
                id="student-pw-student"
                name="student"
                data-cy="student"
                label={translate('toothApp.studentPW.student')}
                type="select"
                required
              >
                <option value="" key="0" />
                {students
                  ? students.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {`${otherEntity.user.firstName} ${otherEntity.user.lastName}`}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField id="student-pw-pw" name="pw" data-cy="pw" label={translate('toothApp.studentPW.pw')} type="select" required>
                <option value="" key="0" />
                {pWS
                  ? pWS.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.title}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="student-pw-academicYear"
                name="academicYear"
                data-cy="academicYear"
                label={translate('toothApp.studentPW.academicYear')}
                type="select"
                required
              >
                <option value="" key="0" />
                {academicYears
                  ? academicYears.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.year}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/student-pw" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default StudentPWUpdate;
