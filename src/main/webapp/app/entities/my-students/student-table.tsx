import React from 'react';

const StudentTable = ({ students }: { students: any }) => {
  return (
    <div className="card">
      <div className="card-header border-0">
        <div className="row">
          <div className="col-6">
            <h3 className="mb-0">List of students</h3>
          </div>
        </div>
      </div>
      <div className="table-responsive">
        <table className="table align-items-center table-flush">
          <thead className="thead-light">
            <tr>
              <th>Name</th>
              <th>CIN</th>
              <th>CNE</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {students.map((student, i) => (
              <tr key={i}>
                <td className="table-user">
                  <img src={`/content/images/uploads/${student.user.imageUrl}`} className="avatar rounded-circle mr-3" />
                  <b>{`${student.user.firstName} ${student.user.lastName}`}</b>
                </td>
                <td>
                  <span className="font-weight-bold">{student.cin}</span>
                </td>
                <td>{student.cne}</td>
                <td className="table-actions">
                  <a href={`/profile/${student.id}`} className="table-action" data-toggle="tooltip" data-original-title="Edit product">
                    <i className="fas fa-user-edit"></i>
                  </a>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default StudentTable;
