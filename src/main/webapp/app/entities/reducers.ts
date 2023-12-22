import professor from 'app/entities/professor/professor.reducer';
import student from 'app/entities/student/student.reducer';
import groupe from 'app/entities/groupe/groupe.reducer';
import tooth from 'app/entities/tooth/tooth.reducer';
import pW from 'app/entities/pw/pw.reducer';
import studentPW from 'app/entities/student-pw/student-pw.reducer';
import academicYear from 'app/entities/academic-year/academic-year.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  professor,
  student,
  groupe,
  tooth,
  pW,
  studentPW,
  academicYear,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
