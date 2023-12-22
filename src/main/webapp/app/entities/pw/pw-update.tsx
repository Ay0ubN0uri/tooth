import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITooth } from 'app/shared/model/tooth.model';
import { getEntities as getTeeth } from 'app/entities/tooth/tooth.reducer';
import { IAcademicYear } from 'app/shared/model/academic-year.model';
import { getEntities as getAcademicYears } from 'app/entities/academic-year/academic-year.reducer';
import { IGroupe } from 'app/shared/model/groupe.model';
import { getEntities as getGroupes } from 'app/entities/groupe/groupe.reducer';
import { IPW } from 'app/shared/model/pw.model';
import { getEntity, updateEntity, createEntity, reset } from './pw.reducer';

export const PWUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const teeth = useAppSelector(state => state.tooth.entities);
  const academicYears = useAppSelector(state => state.academicYear.entities);
  const groupes = useAppSelector(state => state.groupe.entities);
  const pWEntity = useAppSelector(state => state.pW.entity);
  const loading = useAppSelector(state => state.pW.loading);
  const updating = useAppSelector(state => state.pW.updating);
  const updateSuccess = useAppSelector(state => state.pW.updateSuccess);

  const handleClose = () => {
    navigate('/pw');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTeeth({}));
    dispatch(getAcademicYears({}));
    dispatch(getGroupes({}));
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
    values.deadline = convertDateTimeToServer(values.deadline);

    const entity = {
      ...pWEntity,
      ...values,
      groupes: mapIdList(values.groupes),
      tooth: teeth.find(it => it.id.toString() === values.tooth.toString()),
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
          deadline: displayDefaultDateTime(),
        }
      : {
          ...pWEntity,
          deadline: convertDateTimeFromServer(pWEntity.deadline),
          tooth: pWEntity?.tooth?.id,
          academicYear: pWEntity?.academicYear?.id,
          groupes: pWEntity?.groupes?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="toothApp.pW.home.createOrEditLabel" data-cy="PWCreateUpdateHeading">
            <Translate contentKey="toothApp.pW.home.createOrEditLabel">Create or edit a PW</Translate>
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
                <ValidatedField name="id" required readOnly id="pw-id" label={translate('global.field.id')} validate={{ required: true }} />
              ) : null}
              <ValidatedField label={translate('toothApp.pW.title')} id="pw-title" name="title" data-cy="title" type="text" />
              <ValidatedField label={translate('toothApp.pW.objectif')} id="pw-objectif" name="objectif" data-cy="objectif" type="text" />
              <ValidatedField label={translate('toothApp.pW.docs')} id="pw-docs" name="docs" data-cy="docs" type="text" />
              <ValidatedField
                label={translate('toothApp.pW.deadline')}
                id="pw-deadline"
                name="deadline"
                data-cy="deadline"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="pw-tooth" name="tooth" data-cy="tooth" label={translate('toothApp.pW.tooth')} type="select" required>
                <option value="" key="0" />
                {teeth
                  ? teeth.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="pw-academicYear"
                name="academicYear"
                data-cy="academicYear"
                label={translate('toothApp.pW.academicYear')}
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
              <ValidatedField label={translate('toothApp.pW.groupe')} id="pw-groupe" data-cy="groupe" type="select" multiple name="groupes">
                <option value="" key="0" />
                {groupes
                  ? groupes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/pw" replace color="info">
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

export default PWUpdate;
