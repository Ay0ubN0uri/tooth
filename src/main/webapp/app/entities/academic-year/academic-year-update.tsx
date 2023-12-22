import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAcademicYear } from 'app/shared/model/academic-year.model';
import { getEntity, updateEntity, createEntity, reset } from './academic-year.reducer';

export const AcademicYearUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const academicYearEntity = useAppSelector(state => state.academicYear.entity);
  const loading = useAppSelector(state => state.academicYear.loading);
  const updating = useAppSelector(state => state.academicYear.updating);
  const updateSuccess = useAppSelector(state => state.academicYear.updateSuccess);

  const handleClose = () => {
    navigate('/academic-year');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
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
    values.startingDate = convertDateTimeToServer(values.startingDate);
    values.endingDate = convertDateTimeToServer(values.endingDate);

    const entity = {
      ...academicYearEntity,
      ...values,
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
          startingDate: displayDefaultDateTime(),
          endingDate: displayDefaultDateTime(),
        }
      : {
          ...academicYearEntity,
          startingDate: convertDateTimeFromServer(academicYearEntity.startingDate),
          endingDate: convertDateTimeFromServer(academicYearEntity.endingDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="toothApp.academicYear.home.createOrEditLabel" data-cy="AcademicYearCreateUpdateHeading">
            <Translate contentKey="toothApp.academicYear.home.createOrEditLabel">Create or edit a AcademicYear</Translate>
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
                  id="academic-year-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('toothApp.academicYear.year')}
                id="academic-year-year"
                name="year"
                data-cy="year"
                type="text"
              />
              <ValidatedField
                label={translate('toothApp.academicYear.startingDate')}
                id="academic-year-startingDate"
                name="startingDate"
                data-cy="startingDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('toothApp.academicYear.endingDate')}
                id="academic-year-endingDate"
                name="endingDate"
                data-cy="endingDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/academic-year" replace color="info">
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

export default AcademicYearUpdate;
