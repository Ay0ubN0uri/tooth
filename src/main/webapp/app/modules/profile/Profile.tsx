import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getPWsByStudentId } from 'app/entities/my-students/my-students.reducer';
import { getEntity } from 'app/entities/student/student.reducer';
import LoadingSpinner from 'app/shared/components/LoadingSpinner';
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router';
import { Button, ButtonGroup, Card, CardBody, Table, UncontrolledTooltip } from 'reactstrap';

function formatDateString(inputDateString: string): string {
  const dateObject = new Date(inputDateString);

  const options: Intl.DateTimeFormatOptions = {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
  };

  return dateObject.toLocaleString('en-US', options);
}

const Profile = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();
  const loading = useAppSelector(state => state.mystudents.loading);
  const pws = useAppSelector(state => state.mystudents.pws);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    dispatch(getPWsByStudentId({ studentId: id }));
  }, []);

  useEffect(() => {
    if (!loading) {
      setIsLoading(false);
      // setCompleted(true);
    }
  }, [loading]);

  const studentEntity = useAppSelector(state => state.student.entity);
  return (
    <div className="container-fluid">
      <div className="row">
        <div className="col-xl-4 order-xl-2">
          <div className="card card-profile">
            <img src="/content/images/banner.jpg" alt="Image placeholder" className="card-img-top" />
            <div className="row justify-content-center">
              <div className="col-lg-3 order-lg-2">
                <div className="card-profile-image">
                  <a href="#">
                    <img src={`/content/images/uploads/${studentEntity.user?.imageUrl}`} className="rounded-circle" />
                  </a>
                </div>
              </div>
            </div>
            <div className="card-header text-center border-0 pt-8 pt-md-4 pb-0 pb-md-4"></div>
            <div className="card-body pt-0">
              <div className="row">
                <div className="col">
                  <div className="card-profile-stats d-flex justify-content-center">
                    <div>
                      <span className="heading">{studentEntity.cin}</span>
                      <span className="description">CIN</span>
                    </div>
                    <div>
                      <span className="heading">{studentEntity.cne}</span>
                      <span className="description">CNE</span>
                    </div>
                    <div>
                      <span className="heading">{formatDateString(studentEntity.birthDay)}</span>
                      <span className="description">Birth day</span>
                    </div>
                  </div>
                </div>
              </div>
              <div className="text-center">
                <h5 className="h3">{`${studentEntity.user?.firstName} ${studentEntity.user?.lastName}`}</h5>
                <div className="h5 font-weight-300">
                  <i className="ni location_pin mr-2"></i>
                  {studentEntity.user?.email}
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="col-xl-8 order-xl-1">
          {isLoading ? (
            <LoadingSpinner />
          ) : (
            <div className="card">
              <div className="card-header border-0">
                <div className="row">
                  <div className="col-6">
                    <h3 className="mb-0">List of practice works</h3>
                  </div>
                </div>
              </div>
              <div className="table-responsive">
                <table className="table align-items-center table-flush">
                  <thead className="thead-light">
                    <tr>
                      <th>Title</th>
                      <th>Image 1</th>
                      <th>Image 2</th>
                      <th>Date</th>
                    </tr>
                  </thead>
                  <tbody>
                    {pws.map((studentPw, i) => (
                      <tr key={i}>
                        <td className="table-user">
                          <b>{studentPw.pw?.title}</b>
                        </td>
                        <td>
                          <img
                            src={`data:${studentPw.imageFrontContentType};base64,${studentPw.imageFront}`}
                            style={{ maxHeight: '80px' }}
                          />
                        </td>
                        <td>
                          <img src={`data:${studentPw.imageSideContentType};base64,${studentPw.imageSide}`} style={{ maxHeight: '80px' }} />
                        </td>
                        <td className="table-actions">
                          <span className="font-weight-bold">{formatDateString(studentPw.date)}</span>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Profile;
