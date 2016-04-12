package main.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.dao.DatesDAO;
import main.exceptions.DAOException;
import main.exceptions.ServiceException;
import main.model.Dates;

public class DatesSVC {
	private DatesDAO datesDAO;

	public DatesSVC() {
		datesDAO = new DatesDAO();
	}

	public List<Dates> findAll() throws ServiceException {

		List<Dates> list = new ArrayList<Dates>();
		try {
			list = datesDAO.findAll();
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

		return list;
	}
	
	public List<Dates> findAllDatesByDayAndMedClass(Date day, String medClass) throws ServiceException {

		List<Dates> list = new ArrayList<Dates>();
		try {
			list = datesDAO.findAllDatesByDayAndMedClass(day, medClass);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

		return list;
	}

	public void delete(Dates dates) throws ServiceException {

		try {
			datesDAO.delete(dates);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	public boolean insert(Dates dates) throws ServiceException {

		try {
			if (datesDAO.insert(dates) == 1) {
				return true;
			}
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

		return false;
	}

}
