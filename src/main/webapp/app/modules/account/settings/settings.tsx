import React, { useEffect, useState } from 'react';
import { Button, Col, Row } from 'reactstrap';
import { Translate, translate, ValidatedField, ValidatedForm, isEmail, ValidatedBlobField } from 'react-jhipster';
import { toast } from 'react-toastify';

import { locales, languages } from 'app/config/translation';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getSession } from 'app/shared/reducers/authentication';
import { saveAccountSettings, reset, saveProfessorAccountSettings, saveStudentAccountSettings } from './settings.reducer';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import { AUTHORITIES, GRADES } from 'app/config/constants';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';

export const SettingsPage = () => {
  const dispatch = useAppDispatch();
  const account = useAppSelector(state => state.authentication.account);
  const isStudent = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.USER]));
  const isProfessor = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.PROFESSOR]));
  const successMessage = useAppSelector(state => state.settings.successMessage);

  const [imageUrl, setImageUrl] = useState<string>('user.png');

  useEffect(() => {
    dispatch(getSession());
    return () => {
      dispatch(reset());
    };
  }, []);

  useEffect(() => {
    if (successMessage) {
      toast.success(translate(successMessage));
    }
  }, [successMessage]);

  const handleValidSubmit = values => {
    if (values.image === null) {
      values.image = account.image;
      values.imageUrl = account.imageUrl;
    } else {
      values.imageUrl = imageUrl;
    }
    if (isProfessor) {
      dispatch(
        saveProfessorAccountSettings({
          ...account,
          ...values,
        }),
      );
    } else if (isStudent) {
      values.birthDay = convertDateTimeToServer(values.birthDay);
      dispatch(
        saveStudentAccountSettings({
          ...account,
          ...values,
        }),
      );
    } else {
      dispatch(
        saveAccountSettings({
          ...account,
          ...values,
        }),
      );
    }
  };

  const getContentType = url => {
    const extension = url.split('.').pop().toLowerCase();
    switch (extension) {
      case 'jpg':
        return 'image/jpg';
      case 'jpeg':
        return 'image/jpeg';
      case 'png':
        return 'image/png';
      case 'gif':
        return 'image/gif';
      default:
        return 'image/png';
    }
  };

  const defaultValues = () => {
    if (isStudent) {
      return {
        ...account,
        birthDay: convertDateTimeFromServer(account.birthDay),
        image: account.image,
        imageContentType: getContentType(account.imageUrl),
      };
    }
    return {
      ...account,
      image: account.image,
      imageContentType: getContentType(account.imageUrl),
    };
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="settings-title">
            <Translate contentKey="settings.title" interpolate={{ username: account.login }}>
              User settings for {account.login}
            </Translate>
          </h2>
          <ValidatedForm id="settings-form" onSubmit={handleValidSubmit} defaultValues={defaultValues()}>
            <ValidatedField
              name="firstName"
              label={translate('settings.form.firstname')}
              id="firstName"
              placeholder={translate('settings.form.firstname.placeholder')}
              validate={{
                required: { value: true, message: translate('settings.messages.validate.firstname.required') },
                minLength: { value: 1, message: translate('settings.messages.validate.firstname.minlength') },
                maxLength: { value: 50, message: translate('settings.messages.validate.firstname.maxlength') },
              }}
              data-cy="firstname"
            />
            <ValidatedField
              name="lastName"
              label={translate('settings.form.lastname')}
              id="lastName"
              placeholder={translate('settings.form.lastname.placeholder')}
              validate={{
                required: { value: true, message: translate('settings.messages.validate.lastname.required') },
                minLength: { value: 1, message: translate('settings.messages.validate.lastname.minlength') },
                maxLength: { value: 50, message: translate('settings.messages.validate.lastname.maxlength') },
              }}
              data-cy="lastname"
            />
            <ValidatedField
              name="email"
              label={translate('global.form.email.label')}
              placeholder={translate('global.form.email.placeholder')}
              type="email"
              validate={{
                required: { value: true, message: translate('global.messages.validate.email.required') },
                minLength: { value: 5, message: translate('global.messages.validate.email.minlength') },
                maxLength: { value: 254, message: translate('global.messages.validate.email.maxlength') },
                validate: v => isEmail(v) || translate('global.messages.validate.email.invalid'),
              }}
              data-cy="email"
            />
            <ValidatedField type="select" id="langKey" name="langKey" label={translate('settings.form.language')} data-cy="langKey">
              {locales.map(locale => (
                <option value={locale} key={locale}>
                  {languages[locale].name}
                </option>
              ))}
            </ValidatedField>
            <ValidatedBlobField
              label={translate('settings.form.imageUrl')}
              id="image"
              name="image"
              data-cy="image"
              isImage
              accept="image/*"
              onChange={file => {
                setImageUrl(file.target.files.length > 0 ? file.target.files[0].name : 'user.png');
              }}
            />
            {isStudent && (
              <ValidatedField
                name="cne"
                label={translate('settings.form.cne')}
                id="cne"
                placeholder={translate('settings.form.cne.placeholder')}
                validate={{
                  required: { value: true, message: translate('settings.messages.validate.cne.required') },
                  minLength: { value: 10, message: translate('settings.messages.validate.cne.minlength') },
                }}
                data-cy="cne"
              />
            )}
            {isStudent && (
              <ValidatedField
                name="cin"
                label={translate('settings.form.cin')}
                id="cin"
                placeholder={translate('settings.form.cin.placeholder')}
                validate={{
                  required: { value: true, message: translate('settings.messages.validate.cin.required') },
                  minLength: { value: 8, message: translate('settings.messages.validate.cin.minlength') },
                }}
                data-cy="cin"
              />
            )}
            {isStudent && (
              <ValidatedField
                label={translate('settings.form.birthDay')}
                id="birthDay"
                name="birthDay"
                data-cy="birthDay"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
            )}
            {isProfessor && (
              <ValidatedField type="select" id="grade" name="grade" label={'Grade'} data-cy="grade">
                {Object.keys(GRADES).map(grade => (
                  <option key={grade} value={GRADES[grade]}>
                    {grade}
                  </option>
                ))}
              </ValidatedField>
            )}
            <Button color="primary" type="submit" data-cy="submit">
              <Translate contentKey="settings.form.button">Save</Translate>
            </Button>
          </ValidatedForm>
        </Col>
      </Row>
    </div>
  );
};

export default SettingsPage;
