import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProfessor } from 'app/shared/model/professor.model';
import { getEntities as getProfessors } from 'app/entities/professor/professor.reducer';
import { IAcademicYear } from 'app/shared/model/academic-year.model';
import { getEntities as getAcademicYears } from 'app/entities/academic-year/academic-year.reducer';
import { IStudent } from 'app/shared/model/student.model';
import { getEntities as getStudents } from 'app/entities/student/student.reducer';
import { IPW } from 'app/shared/model/pw.model';
import { getEntities as getPWs } from 'app/entities/pw/pw.reducer';
import { IGroupe } from 'app/shared/model/groupe.model';
import { getEntity, updateEntity, createEntity, reset } from './groupe.reducer';

export const GroupeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const professors = useAppSelector(state => state.professor.entities);
  const academicYears = useAppSelector(state => state.academicYear.entities);
  const students = useAppSelector(state => state.student.entities);
  const pWS = useAppSelector(state => state.pW.entities);
  const groupeEntity = useAppSelector(state => state.groupe.entity);
  const loading = useAppSelector(state => state.groupe.loading);
  const updating = useAppSelector(state => state.groupe.updating);
  const updateSuccess = useAppSelector(state => state.groupe.updateSuccess);

  const handleClose = () => {
    navigate('/groupe');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProfessors({}));
    dispatch(getAcademicYears({}));
    dispatch(getStudents({}));
    dispatch(getPWs({}));
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

    const entity = {
      ...groupeEntity,
      ...values,
      professor: professors.find(it => it.id.toString() === values.professor.toString()),
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
      ? {}
      : {
          ...groupeEntity,
          professor: groupeEntity?.professor?.id,
          academicYear: groupeEntity?.academicYear?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="toothApp.groupe.home.createOrEditLabel" data-cy="GroupeCreateUpdateHeading">
            <Translate contentKey="toothApp.groupe.home.createOrEditLabel">Create or edit a Groupe</Translate>
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
                  id="groupe-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('toothApp.groupe.code')} id="groupe-code" name="code" data-cy="code" type="text" />
              <ValidatedField label={translate('toothApp.groupe.year')} id="groupe-year" name="year" data-cy="year" type="text" />
              <ValidatedField
                id="groupe-professor"
                name="professor"
                data-cy="professor"
                label={translate('toothApp.groupe.professor')}
                type="select"
                required
              >
                <option value="" key="0" />
                {professors
                  ? professors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="groupe-academicYear"
                name="academicYear"
                data-cy="academicYear"
                label={translate('toothApp.groupe.academicYear')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/groupe" replace color="info">
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

export default GroupeUpdate;
